import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CurrencyConverter {
    // URL base da API de conversão de moeda
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char choice;

        do {
            System.out.println("*********************************");
            System.out.println("Bem-vindo ao Conversor de Moedas!");
            System.out.println("*********************************");

            // Apresentar o menu de opções
            System.out.println("\nEscolha uma opção de conversão:");
            System.out.println("1. USD para EUR");
            System.out.println("2. EUR para USD");
            System.out.println("3. USD para JPY");
            System.out.println("4. JPY para USD");
            System.out.println("5. EUR para JPY");
            System.out.println("6. JPY para EUR");
            System.out.println("\n*********************************");

            // Solicitar a opção desejada
            System.out.print("Digite o número da opção desejada: ");
            int option = scanner.nextInt();

            // Definindo a moeda de origem e de destino com base na escolha do usuário
            String baseCurrency = "";
            String targetCurrency = "";
            switch (option) {
                case 1:
                    baseCurrency = "USD";
                    targetCurrency = "EUR";
                    break;
                case 2:
                    baseCurrency = "EUR";
                    targetCurrency = "USD";
                    break;
                case 3:
                    baseCurrency = "USD";
                    targetCurrency = "JPY";
                    break;
                case 4:
                    baseCurrency = "JPY";
                    targetCurrency = "USD";
                    break;
                case 5:
                    baseCurrency = "EUR";
                    targetCurrency = "JPY";
                    break;
                case 6:
                    baseCurrency = "JPY";
                    targetCurrency = "EUR";
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }

            if (!baseCurrency.isEmpty() && !targetCurrency.isEmpty()) {
                // Solicitar o valor a ser convertido
                System.out.print("Digite o valor a ser convertido: ");
                // Solicitar ao usuário o valor a ser convertido e armazená-lo na variável "amount"
                double amount = scanner.nextDouble();

                // Realizar a conversão
                double convertedAmount = realizarConversao(baseCurrency, targetCurrency, amount);

                // Exibir o resultado da conversão
                System.out.println(amount + " " + baseCurrency + " equivale a " + convertedAmount + " " + targetCurrency);
            }

            // Solicitar se deseja fazer outra conversão
            System.out.print("Deseja fazer outra conversão? (S/N): ");
            choice = scanner.next().toUpperCase().charAt(0);

        } while (choice == 'S');

        System.out.println("Obrigado por usar o Conversor de Moedas!");
        scanner.close();
    }

    // Método para realizar a conversão de moeda
    private static double realizarConversao(String baseCurrency, String targetCurrency, double amount) {
        String completeURL = API_URL + baseCurrency;
        double exchangeRate = 0.0;

        try {
            // Conectando-se à API e obtendo os dados de conversão
            URL url = new URL(completeURL);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();

            // Extraindo as taxas de conversão do objeto JSON
            JsonObject rates = jsonObject.getAsJsonObject("rates");

            // Obtendo a taxa de conversão para a moeda de destino escolhida
            exchangeRate = rates.get(targetCurrency).getAsDouble();

        } catch (Exception e) {
            System.out.println("Ocorreu um erro: " + e.getMessage());
        }

        return amount * exchangeRate;
    }
}

