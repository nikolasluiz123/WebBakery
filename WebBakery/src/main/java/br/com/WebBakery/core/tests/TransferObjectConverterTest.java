package br.com.WebBakery.core.tests;

import org.junit.Assert;
import org.junit.Test;

import br.com.WebBakery.core.TransferObjectConverter;

public class TransferObjectConverterTest {

    @Test
    public void testGetTOFromModel() {
        ModelExample model = new ModelExample();
        model.setId(1);
        model.setNome("Ruan");
        model.setNota(10.0);

        NestedModel nestedModel = new NestedModel();
        nestedModel.setNome("Joriscleidson");

        model.setNestedModel(nestedModel);

        TOExample to = new TOExample();

        try {
            new TransferObjectConverter().getTOFromModel(model, to);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(model.getNome().equals(to.getNome())
                && model.getNota().equals(to.getNota()) && model.getId().equals(to.getId()));
    }

    @Test
    public void testGetTOFromModelWithNestedTO() {
        ModelExample model = new ModelExample();
        model.setId(1);
        model.setNome("Ruan");
        model.setNota(10.0);

        NestedModel nestedModel = new NestedModel();
        nestedModel.setId(12);
        nestedModel.setNome("Nikolas");

        model.setNestedModel(nestedModel);

        TOExample to = new TOExample();

        try {
            new TransferObjectConverter().getTOFromModel(model, to);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(model.getNestedModel().getNome().equals(to.getNestedTO().getNome())
                && model.getId().equals(to.getId()));
    }

    @Test
    public void testGetModelFromTO() {
        TOExample toExample = new TOExample();
        toExample.setId(1);
        toExample.setNome("Níkolas");
        toExample.setNota(8.0);

        NestedTO nestedTO = new NestedTO();
        nestedTO.setId(1);
        nestedTO.setNome("Ruan");

        toExample.setNestedTO(nestedTO);

        ModelExample modelExample = new ModelExample();

        try {
            new TransferObjectConverter().getModelFromTO(toExample, modelExample);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(toExample.getNome().equals(modelExample.getNome())
                && toExample.getNota().equals(modelExample.getNota()));

    }

    @Test
    public void testGetModelFromTOWithNestedModel() {
        TOExample toExample = new TOExample();
        toExample.setId(1);
        toExample.setNome("Níkolas");
        toExample.setNota(8.0);

        NestedTO nestedTO = new NestedTO();
        nestedTO.setId(1);
        nestedTO.setNome("Ruan");

        toExample.setNestedTO(nestedTO);

        ModelExample modelExample = new ModelExample();

        try {
            new TransferObjectConverter().getModelFromTO(toExample, modelExample);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(toExample.getNestedTO().getNome()
                .equals(modelExample.getNestedModel().getNome()));
    }

    @Test
    public void testGetTOFromModelWithNestedTONull() {
        ModelExample model = new ModelExample();
        model.setNome("Ruan");
        model.setNota(10.0);

        TOExample to = new TOExample();

        try {
            new TransferObjectConverter().getTOFromModel(model, to);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNull(to.getNestedTO());
    }

    @Test
    public void testGetModelFromTOWithNestedModelNull() {
        TOExample to = new TOExample();
        to.setNome("Ruan");
        to.setNota(10.0);

        ModelExample model = new ModelExample();

        try {
            new TransferObjectConverter().getModelFromTO(to, model);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNull(model.getNestedModel());
    }
}
