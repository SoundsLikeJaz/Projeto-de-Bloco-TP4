package com.example.cardapio.frontendTests.test;

import com.example.cardapio.frontendTests.core.BaseTest;
import com.example.cardapio.frontendTests.pages.CardapioPage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CardapioTestIT extends BaseTest {
    @Test
    void deveCadastrarItem() throws InterruptedException {
        CardapioPage cardapioPage = new CardapioPage(driver).abrir()
                .clicarCadastrarProduto()
                .preencherProdutoTitulo("Lasanha")
                .preencherProdutoUrl("https://guiadacozinha.com.br/wp-content/uploads/2014/01/lasanha-bolonhesa-na-pressao.jpg")
                .preencherProdutoPreco("19.00")
                .clicarSubmitButton();

        String result = cardapioPage.getLastItemTitle();

        assertEquals("Lasanha", result);
    }

    @Test
    void deveEditarItem() throws InterruptedException {
        CardapioPage cardapioPage = new CardapioPage(driver).abrir()
                .clicarCadastrarProduto()
                .preencherProdutoTitulo("Pizza")
                .preencherProdutoUrl("https://images.aws.nestle.recipes/original/1821a30a8a8acec9f74a1372be582610_sem_t%C3%ADtulo_(18).jpg")
                .preencherProdutoPreco("55.50")
                .clicarSubmitButton();

        assertEquals("Pizza", cardapioPage.getLastItemTitle());

        cardapioPage = cardapioPage.clicarEditLastItem()
                .preencherProdutoTitulo("Pizza Margherita")
                .clicarSubmitButton();

        assertEquals("Pizza Margherita", cardapioPage.getLastItemTitle());
    }

    @Test
    void deveDeletarItem() throws InterruptedException {
        CardapioPage cardapioPage = new CardapioPage(driver).abrir()
                .clicarCadastrarProduto()
                .preencherProdutoTitulo("Tapioca")
                .preencherProdutoUrl("https://static.wixstatic.com/media/a85c73_8e4f04aa2906481bb6945e4660aeb9bf~mv2.jpg/v1/fill/w_618,h_450,fp_0.50_0.41,q_80,enc_avif,quality_auto/capa-tapioca-tradicional.jpg")
                .preencherProdutoPreco("10")
                .clicarSubmitButton();

        assertEquals("Tapioca", cardapioPage.getLastItemTitle());

        cardapioPage = cardapioPage.clicarDeleteLastItem()
                .clicarConfirmDeleteItem();

        assertNotEquals("Tapioca", cardapioPage.getLastItemTitle());
    }

    @Test
    void naoDeveDeletarItem() throws InterruptedException {
        CardapioPage cardapioPage = new CardapioPage(driver).abrir()
                .clicarCadastrarProduto()
                .preencherProdutoTitulo("Brigadeiro")
                .preencherProdutoUrl("https://simplelivingrecipes.com/wp-content/uploads/2022/09/brigadeiro-recipe-s.jpg")
                .preencherProdutoPreco("5")
                .clicarSubmitButton();

        assertEquals("Brigadeiro", cardapioPage.getLastItemTitle());

        cardapioPage = cardapioPage.clicarDeleteLastItem()
                .clicarDenyDeleteItem();

        assertEquals("Brigadeiro", cardapioPage.getLastItemTitle());
    }

    @Test
    void naoDeveCadastrarItem() {
        CardapioPage cardapioPage = new CardapioPage(driver).abrir()
                .clicarCadastrarProduto()
                .preencherProdutoTitulo("")
                .preencherProdutoUrl("https://simplelivingrecipes.com/wp-content/uploads/2022/09/brigadeiro-recipe-s.jpg")
                .preencherProdutoPreco("-5")
                .clicarSubmitButton();

        assertNotNull(cardapioPage.getErrorMessage());
    }
}
