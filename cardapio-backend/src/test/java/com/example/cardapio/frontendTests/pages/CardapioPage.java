package com.example.cardapio.frontendTests.pages;

import com.example.cardapio.frontendTests.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CardapioPage extends BasePage {
    private final String url = "http://localhost:5173";

    private static final By cadastrarButton = By.xpath("//*[@id=\"root\"]/div/button");
    private static final By tituloInputField = By.xpath("//*[@id=\"root\"]/div/div[2]/div/form/input[1]");
    private static final By urlInputField = By.xpath("//*[@id=\"root\"]/div/div[2]/div/form/input[2]");
    private static final By precoInputField = By.xpath("//*[@id=\"root\"]/div/div[2]/div/form/input[3]");
    private static final By submitButton = By.xpath("//*[@id=\"root\"]/div/div[2]/div/button");
    private static final By lastItemEditButton = By.xpath("//*[@id=\"root\"]/div/div/div[last()]//button[1]");
    private static final By lastItemDeleteButton = By.xpath("//*[@id=\"root\"]/div/div/div[last()]//button[2]");
    private static final By confirmDeleteButton = By.cssSelector("button.confirm-btn.confirm-yes");
    private static final By denyDeleteButton = By.cssSelector("button.confirm-btn.confirm-no");
    private static final By errorMessage = By.cssSelector("div.modal-overlay > div > p");


    private static final By lastItemTitle = By.xpath("//*[@id=\"root\"]/div/div/div[last()]/h2");

    public CardapioPage(WebDriver driver) {
        super(driver);
    }

    public CardapioPage abrir() {
        driver.get(url);
        return this;
    }

    public CardapioPage clicarCadastrarProduto() {
        click(cadastrarButton);
        return this;
    }

    public CardapioPage preencherProdutoTitulo(String titulo) {
        type(tituloInputField, titulo);
        return this;
    }

    public CardapioPage preencherProdutoUrl(String url) {
        type(urlInputField, url);
        return this;
    }

    public CardapioPage preencherProdutoPreco(String preco) {
        type(precoInputField, preco);
        return this;
    }

    public CardapioPage clicarSubmitButton() {
        click(submitButton);
        return this;
    }

    public String getLastItemTitle() throws InterruptedException {
        // Infelizmente dá erro sem o Thread sleep minúsculo
        Thread.sleep(100);
        return $(lastItemTitle).getText();
    }

    public CardapioPage clicarEditLastItem() {
        click(lastItemEditButton);
        return this;
    }

    public CardapioPage clicarDeleteLastItem() {
        click(lastItemDeleteButton);
        return this;
    }

    public CardapioPage clicarConfirmDeleteItem() {
        click(confirmDeleteButton);
        return this;
    }

    public CardapioPage clicarDenyDeleteItem() {
        click(denyDeleteButton);
        return this;
    }

    public String getErrorMessage() {
        return $(errorMessage).getText();
    }
}
