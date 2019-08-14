package cidade;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import br.com.WebBakery.bean.manutencao.CidadeBean;
import br.com.WebBakery.to.TOCidade;
import br.com.WebBakery.to.TOEstado;
import br.com.WebBakery.to.TOPais;

class TesteCadastros {

    @Test
    void TestCadastroComTodosOsDadosCorretos() {
        CidadeBean bean = new CidadeBean();
        
        TOPais toPais = new TOPais();
        toPais.setAtivo(true);
        toPais.setNome("pais1");
        toPais.setSigla("p1");
        
        TOEstado toEstado = new TOEstado();
        toEstado.setAtivo(true);
        toEstado.setNome("estado1");
        toEstado.setSigla("e1");
        toEstado.setToPais(toPais);
        
        TOCidade toCidade = new TOCidade();
        toCidade.setAtivo(true);
        toCidade.setNome("cidade1");
        toCidade.setToEstado(toEstado);
        
        bean.setTo(toCidade);
        bean.salvar();
    }

}
