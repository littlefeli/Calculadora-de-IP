package br.dev.felipe.ipcalc.model;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPCalculadora {

    private String endereco;
    private int prefixo;
    private int[] ipBinario = new int[32];

    public IPCalculadora(String endereco, int prefixo) throws UnknownHostException {
        this.endereco = endereco;
        this.prefixo = prefixo;
        converterIPParaBinario();
    }

    private void converterIPParaBinario() throws UnknownHostException {
        byte[] bytes = InetAddress.getByName(endereco).getAddress();
        for (int i = 0; i < 4; i++) {
            int val = bytes[i] & 0xFF;
            for (int j = 7; j >= 0; j--) {
                ipBinario[i * 8 + (7 - j)] = (val >> j) & 1;
            }
        }
    }

    public String getResumo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Endereço IP: ").append(endereco).append("/").append(prefixo).append("\n");
        sb.append("Máscara: ").append(obterMascara()).append("\n");
        sb.append("Número de hosts: ").append(numeroDeHosts()).append("\n");
        sb.append("Endereço de rede: ").append(obterEnderecoRede()).append("\n");
        sb.append("Endereço de broadcast: ").append(obterBroadcast()).append("\n");
        return sb.toString();
    }

    private String obterMascara() {
        int[] mascara = new int[4];
        for (int i = 0; i < prefixo; i++) {
            ipBinario[i] = 1;
        }
        for (int i = 0; i < 4; i++) {
            mascara[i] = 0;
            for (int j = 0; j < 8; j++) {
                mascara[i] += ipBinario[i * 8 + j] << (7 - j);
            }
        }
        return String.format("%d.%d.%d.%d", mascara[0], mascara[1], mascara[2], mascara[3]);
    }

    private int numeroDeHosts() {
        return (int) Math.pow(2, 32 - prefixo) - 2;
    }

    private String obterEnderecoRede() {
        int[] ipBytes = new int[4];
        int[] mascara = new int[4];

        for (int i = 0; i < 4; i++) {
            ipBytes[i] = Integer.parseInt(endereco.split("\\.") [i]);
        }

        int bits = prefixo;
        for (int i = 0; i < 4; i++) {
            mascara[i] = bits >= 8 ? 255 : (bits > 0 ? (256 - (1 << (8 - bits))) : 0);
            bits -= 8;
        }

        return String.format("%d.%d.%d.%d",
                (ipBytes[0] & mascara[0]),
                (ipBytes[1] & mascara[1]),
                (ipBytes[2] & mascara[2]),
                (ipBytes[3] & mascara[3]));
    }

    private String obterBroadcast() {
        int[] ipBytes = new int[4];
        int[] mascara = new int[4];

        for (int i = 0; i < 4; i++) {
            ipBytes[i] = Integer.parseInt(endereco.split("\\.") [i]);
        }

        int bits = prefixo;
        for (int i = 0; i < 4; i++) {
            mascara[i] = bits >= 8 ? 255 : (bits > 0 ? (256 - (1 << (8 - bits))) : 0);
            bits -= 8;
        }

        return String.format("%d.%d.%d.%d",
                (ipBytes[0] | ~mascara[0] & 0xFF),
                (ipBytes[1] | ~mascara[1] & 0xFF),
                (ipBytes[2] | ~mascara[2] & 0xFF),
                (ipBytes[3] | ~mascara[3] & 0xFF));
    }
}