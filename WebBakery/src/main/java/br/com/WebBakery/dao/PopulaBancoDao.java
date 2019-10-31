package br.com.WebBakery.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.WebBakery.enums.TipoUsuario;
import br.com.WebBakery.model.entitys.Cidade;
import br.com.WebBakery.model.entitys.Cliente;
import br.com.WebBakery.model.entitys.Endereco;
import br.com.WebBakery.model.entitys.Estado;
import br.com.WebBakery.model.entitys.Funcionario;
import br.com.WebBakery.model.entitys.Logradouro;
import br.com.WebBakery.model.entitys.Pais;
import br.com.WebBakery.model.entitys.Usuario;
import br.com.WebBakery.util.HashTypeEnum;
import br.com.WebBakery.util.Hash_Util;

@Stateless
public class PopulaBancoDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext
    transient public EntityManager em;

    public PopulaBancoDao(EntityManager em) {
        this.em = em;
    }

    public PopulaBancoDao() {
    }

    public void popularBanco() {
        Pais pais1 = gerarPais("Afeganistão", "AF");
        Pais pais2 = gerarPais("África Do Sul", "ZA");
        Pais pais3 = gerarPais("Albânia", "AL");
        Pais pais4 = gerarPais("Alemanha", "DE");
        Pais pais5 = gerarPais("America", "AS");
        Pais pais6 = gerarPais("Samoa", "AD");
        Pais pais7 = gerarPais("Andorra", "AI");
        Pais pais8 = gerarPais("Anguilla", "AG");
        Pais pais9 = gerarPais("Antígua e Barbuda", "AN");
        Pais pais10 = gerarPais("Antilhas Holandesas", "SA");
        Pais pais11 = gerarPais("Arábia Saudita", "DZ");
        Pais pais12 = gerarPais("Argélia", "AR");
        Pais pais13 = gerarPais("Argentina", "AM");
        Pais pais14 = gerarPais("Arménia", "AW");
        Pais pais15 = gerarPais("Aruba", "AU");
        Pais pais16 = gerarPais("Austrália", "AT");
        Pais pais17 = gerarPais("Áustria", "AZ");
        Pais pais18 = gerarPais("Azerbaidjão", "BS");
        Pais pais19 = gerarPais("Bahamas", "BD");
        Pais pais20 = gerarPais("Bangladeche", "BB");
        Pais pais21 = gerarPais("Barbados", "BH");
        Pais pais22 = gerarPais("Bareine", "BE");
        Pais pais23 = gerarPais("Bélgica", "BZ");
        Pais pais24 = gerarPais("Belize", "BJ");
        Pais pais25 = gerarPais("Benim", "BM");
        Pais pais26 = gerarPais("Bermudas", "BO");
        Pais pais27 = gerarPais("Bolívia", "BA");
        Pais pais28 = gerarPais("Bósnia e Herzegóvina", "BW");
        Pais pais29 = gerarPais("Botsuana", "BN");
        Pais pais30 = gerarPais("Brasil", "BR");
        Pais pais31 = gerarPais("Brunei Darussalam", "BD");
        Pais pais32 = gerarPais("Bulgária", "BG");
        Pais pais33 = gerarPais("Burkina Faso", "BF");
        Pais pais34 = gerarPais("Burundi", "BI");
        Pais pais35 = gerarPais("Butão", "BT");
        Pais pais36 = gerarPais("Cabo Verde", "CV");
        Pais pais37 = gerarPais("Camarões", "CM");
        Pais pais38 = gerarPais("Camboja", "KH");
        Pais pais39 = gerarPais("Canadá", "CA");
        Pais pais40 = gerarPais("Catar", "QA");
        Pais pais41 = gerarPais("Cazaquistão", "KZ");
        Pais pais42 = gerarPais("Chade", "TD");
        Pais pais43 = gerarPais("Chile", "CL");
        Pais pais44 = gerarPais("China", "CN");
        Pais pais45 = gerarPais("Chipre", "CY");
        Pais pais46 = gerarPais("Colômbia", "CO");
        Pais pais47 = gerarPais("Comores", "KM");
        Pais pais48 = gerarPais("Congo", "CG");
        Pais pais49 = gerarPais("Costa Do Marfim", "CI");
        Pais pais50 = gerarPais("Costa Rica", "CR");
        Pais pais51 = gerarPais("Croácia", "HR");
        Pais pais52 = gerarPais("Cuba", "CU");
        Pais pais53 = gerarPais("Dinamarca", "DK");
        Pais pais54 = gerarPais("Domínica", "DM");
        Pais pais55 = gerarPais("Egito", "EG");
        Pais pais56 = gerarPais("Emirados Árabes Unidos", "AE");
        Pais pais57 = gerarPais("Equador", "EC");
        Pais pais58 = gerarPais("Eritreia", "ER");
        Pais pais59 = gerarPais("Eslováquia", "SK");
        Pais pais60 = gerarPais("Eslovénia", "SI");
        Pais pais61 = gerarPais("Espanha", "ES");
        Pais pais62 = gerarPais("Estados Unidos", "US");
        Pais pais63 = gerarPais("Estónia", "EE");
        Pais pais64 = gerarPais("Etiópia", "ET");
        Pais pais65 = gerarPais("Fiji", "FJ");
        Pais pais66 = gerarPais("Filipinas", "PH");
        Pais pais67 = gerarPais("Finlândia", "FI");
        Pais pais68 = gerarPais("França", "FR");
        Pais pais69 = gerarPais("Gabão", "GA");
        Pais pais70 = gerarPais("Gâmbia", "GM");
        Pais pais71 = gerarPais("Gana", "GH");
        Pais pais72 = gerarPais("Geórgia", "GE");
        Pais pais73 = gerarPais("Gibraltar", "GI");
        Pais pais74 = gerarPais("Granada", "GD");
        Pais pais75 = gerarPais("Grécia", "GL");
        Pais pais76 = gerarPais("Gronelândia", "GR");
        Pais pais77 = gerarPais("Guadalupe", "GP");
        Pais pais78 = gerarPais("Guam", "GU");
        Pais pais79 = gerarPais("Guatemala", "GT");
        Pais pais80 = gerarPais("Guiana", "GV");
        Pais pais81 = gerarPais("Guiné", "GQ");
        Pais pais82 = gerarPais("Guiné Equatorial", "GW");
        Pais pais83 = gerarPais("Guiné-Bissau", "HT");
        Pais pais84 = gerarPais("Honduras", "HN");
        Pais pais85 = gerarPais("Hong Kong", "HK");
        Pais pais86 = gerarPais("Hungria", "HU");
        Pais pais87 = gerarPais("Iémen", "YE");
        Pais pais88 = gerarPais("Ilha Bouvet", "BV");
        Pais pais89 = gerarPais("Índia", "IN");
        Pais pais90 = gerarPais("Indonésia", "ID");
        Pais pais91 = gerarPais("Irã", "IR");
        Pais pais92 = gerarPais("Iraque", "IQ");
        Pais pais93 = gerarPais("Irlanda", "IE");
        Pais pais94 = gerarPais("Islândia", "IS");
        Pais pais95 = gerarPais("Israel", "IL");
        Pais pais96 = gerarPais("Itália", "IT");
        Pais pais97 = gerarPais("Jamaica", "JM");
        Pais pais98 = gerarPais("Japão", "JP");
        Pais pais99 = gerarPais("Jersey", "JE");
        Pais pais100 = gerarPais("Djibuti", "DJ");
        Pais pais101 = gerarPais("Jordânia", "JO");
        Pais pais102 = gerarPais("Kiribati", "KI");
        Pais pais103 = gerarPais("Koweit", "KW");
        Pais pais104 = gerarPais("Laos", "LA");
        Pais pais105 = gerarPais("Lesoto", "LA");
        Pais pais106 = gerarPais("Letónia", "LV");
        Pais pais107 = gerarPais("Líbano", "LB");
        Pais pais108 = gerarPais("Libéria", "LR");
        Pais pais109 = gerarPais("Líbia", "LY");
        Pais pais110 = gerarPais("Lituânia", "LT");
        Pais pais111 = gerarPais("Luxemburgo", "LU");
        Pais pais112 = gerarPais("Macau", "MO");
        Pais pais113 = gerarPais("Madagáscar", "MG");
        Pais pais114 = gerarPais("Malásia", "MY");
        Pais pais115 = gerarPais("Malavi", "MW");
        Pais pais116 = gerarPais("Maldivas", "MV");
        Pais pais117 = gerarPais("Mali", "ML");
        Pais pais118 = gerarPais("Malta", "MT");
        Pais pais119 = gerarPais("Marrocos", "MA");
        Pais pais120 = gerarPais("Martinica", "MQ");
        Pais pais121 = gerarPais("Maurícia", "MU");
        Pais pais122 = gerarPais("Mauritânia", "MR");
        Pais pais123 = gerarPais("México", "MX");
        Pais pais124 = gerarPais("Micronésia", "FM");
        Pais pais125 = gerarPais("Moçambique", "MZ");
        Pais pais126 = gerarPais("Mónaco", "MC");
        Pais pais127 = gerarPais("Mongólia", "MN");
        Pais pais128 = gerarPais("Montenegro", "ME");
        Pais pais129 = gerarPais("Namíbia", "NA");
        Pais pais130 = gerarPais("Nauru", "NR");
        Pais pais131 = gerarPais("Nepal", "NP");
        Pais pais132 = gerarPais("Nicarágua", "NI");
        Pais pais133 = gerarPais("Níger", "NE");
        Pais pais134 = gerarPais("Noruega", "NO");
        Pais pais135 = gerarPais("Nova CAledónia", "NC");
        Pais pais136 = gerarPais("Nova Zelândia", "NZ");
        Pais pais137 = gerarPais("Omã", "OM");
        Pais pais138 = gerarPais("Palau", "PW");
        Pais pais139 = gerarPais("Panamá", "PA");
        Pais pais140 = gerarPais("Paquistão", "PK");
        Pais pais141 = gerarPais("Paraguai", "PY");
        Pais pais142 = gerarPais("Peru", "PE");
        Pais pais143 = gerarPais("Polinésia Francesa", "PF");
        Pais pais144 = gerarPais("Polónia", "PL");
        Pais pais145 = gerarPais("Porto rico", "PR");
        Pais pais146 = gerarPais("Portugal", "PT");
        Pais pais147 = gerarPais("Quênia", "KE");
        Pais pais148 = gerarPais("Quirguizistão", "KG");
        Pais pais149 = gerarPais("Reino Unido", "GB");
        Pais pais150 = gerarPais("República Checa", "CZ");
        Pais pais151 = gerarPais("República Dominicana", "DO");
        Pais pais152 = gerarPais("Roménia", "RO");
        Pais pais153 = gerarPais("Ruanda", "RW");
        Pais pais154 = gerarPais("Rússia", "RU");
        Pais pais155 = gerarPais("Salvador", "SV");
        Pais pais156 = gerarPais("Samoa", "SA");
        Pais pais157 = gerarPais("Santa Lúcia", "LC");
        Pais pais158 = gerarPais("São cristóvão e Neves", "KN");
        Pais pais159 = gerarPais("São Marino", "SM");
        Pais pais160 = gerarPais("São Tomé e Príncipe", "ST");
        Pais pais161 = gerarPais("São Vicente e Granadinas", "VC");
        Pais pais162 = gerarPais("Senegal", "SN");
        Pais pais163 = gerarPais("Serra Leoa", "SL");
        Pais pais164 = gerarPais("Sérvia", "RS");
        Pais pais165 = gerarPais("Singapura", "SG");
        Pais pais166 = gerarPais("Síria", "SY");
        Pais pais167 = gerarPais("Somália", "SO");
        Pais pais168 = gerarPais("Sri Lanca", "LK");
        Pais pais169 = gerarPais("Suazilândia", "SZ");
        Pais pais170 = gerarPais("Sudão", "SD");
        Pais pais171 = gerarPais("Suécia", "SE");
        Pais pais172 = gerarPais("Suíça", "CH");
        Pais pais173 = gerarPais("Suriname", "SR");
        Pais pais174 = gerarPais("Tailândia", "TH");
        Pais pais175 = gerarPais("Taiwan", "TW");
        Pais pais176 = gerarPais("Tajiquistão", "TJ");
        Pais pais177 = gerarPais("Timor-leste", "TL");
        Pais pais178 = gerarPais("Togo", "TG");
        Pais pais179 = gerarPais("Tokelau", "TK");
        Pais pais180 = gerarPais("Tonga", "TO");
        Pais pais181 = gerarPais("Trindade e Tobago", "TT");
        Pais pais182 = gerarPais("Turquia", "TR");
        Pais pais183 = gerarPais("Tuvalu", "TV");
        Pais pais184 = gerarPais("Ucrânia", "UA");
        Pais pais185 = gerarPais("Uganda", "UG");
        Pais pais186 = gerarPais("Uruguai", "UY");
        Pais pais187 = gerarPais("Usbequistão", "UZ");

        Estado estado1 = gerarEstado("Acre", "AC", pais30);
        Estado estado2 = gerarEstado("Alagoas", "AL", pais30);
        Estado estado3 = gerarEstado("Amapá", "AP", pais30);
        Estado estado4 = gerarEstado("Amazonas", "AM", pais30);
        Estado estado5 = gerarEstado("Bahia", "BA", pais30);
        Estado estado6 = gerarEstado("Ceará", "CE", pais30);
        Estado estado7 = gerarEstado("Distrito Federal", "DF", pais30);
        Estado estado8 = gerarEstado("Espírito Santo", "ES", pais30);
        Estado estado9 = gerarEstado("Goiás", "GO", pais30);
        Estado estado10 = gerarEstado("Maranhão", "MA", pais30);
        Estado estado11 = gerarEstado("Mato Grosso", "MT", pais30);
        Estado estado12 = gerarEstado("Mato Grosso do Sul", "MS", pais30);
        Estado estado13 = gerarEstado("Minas Gerais", "MG", pais30);
        Estado estado14 = gerarEstado("Pará", "PA", pais30);
        Estado estado15 = gerarEstado("Paraíba", "PB", pais30);
        Estado estado16 = gerarEstado("Paraná", "PR", pais30);
        Estado estado17 = gerarEstado("Pernambuco", "PE", pais30);
        Estado estado18 = gerarEstado("Piauí", "PI", pais30);
        Estado estado19 = gerarEstado("Rio de Janeiro", "RJ", pais30);
        Estado estado20 = gerarEstado("Rio Grande do Norte", "RN", pais30);
        Estado estado21 = gerarEstado("Rio Grande do Sul", "RS", pais30);
        Estado estado22 = gerarEstado("Rondônia", "RO", pais30);
        Estado estado23 = gerarEstado("Roraima", "RR", pais30);
        Estado estado24 = gerarEstado("Santa Catarina", "SC", pais30);
        Estado estado25 = gerarEstado("São Paulo", "SP", pais30);
        Estado estado26 = gerarEstado("Sergipe", "SE", pais30);
        Estado estado27 = gerarEstado("Tocantins", "TO", pais30);

        Cidade cidade1 = gerarCidade("Abdon Batista", estado24);
        Cidade cidade2 = gerarCidade("Abelardo Luz", estado24);
        Cidade cidade3 = gerarCidade("Agrolandia", estado24);
        Cidade cidade4 = gerarCidade("Agronomica", estado24);
        Cidade cidade5 = gerarCidade("Agua Doce", estado24);
        Cidade cidade6 = gerarCidade("Aguas Frias", estado24);
        Cidade cidade7 = gerarCidade("Aguas Mornas", estado24);
        Cidade cidade8 = gerarCidade("Aguas de Chapeco", estado24);
        Cidade cidade9 = gerarCidade("Alfredo Wagner", estado24);
        Cidade cidade10 = gerarCidade("Alto Bela Vista", estado24);
        Cidade cidade11 = gerarCidade("Anchieta", estado24);
        Cidade cidade12 = gerarCidade("Angelina", estado24);
        Cidade cidade13 = gerarCidade("Anita Garibaldi", estado24);
        Cidade cidade14 = gerarCidade("Anitapolis", estado24);
        Cidade cidade15 = gerarCidade("Antonio Carlos", estado24);
        Cidade cidade16 = gerarCidade("Apiuna", estado24);
        Cidade cidade17 = gerarCidade("Arabuta", estado24);
        Cidade cidade18 = gerarCidade("Araquari", estado24);
        Cidade cidade19 = gerarCidade("Ararangua", estado24);
        Cidade cidade20 = gerarCidade("Armazem", estado24);
        Cidade cidade21 = gerarCidade("Arroio Trinta", estado24);
        Cidade cidade22 = gerarCidade("Arvoredo", estado24);
        Cidade cidade23 = gerarCidade("Ascurra", estado24);
        Cidade cidade24 = gerarCidade("Atalanta", estado24);
        Cidade cidade25 = gerarCidade("Aurora", estado24);
        Cidade cidade26 = gerarCidade("Balneario Arroio do Silva", estado24);
        Cidade cidade27 = gerarCidade("Balneario Barra do Sul", estado24);
        Cidade cidade28 = gerarCidade("Balneario Camboriu", estado24);
        Cidade cidade29 = gerarCidade("Balneario Gaivota", estado24);
        Cidade cidade30 = gerarCidade("Bandeirante", estado24);
        Cidade cidade31 = gerarCidade("Barra Bonita", estado24);
        Cidade cidade32 = gerarCidade("Barra Velha", estado24);
        Cidade cidade33 = gerarCidade("Bela Vista do Toldo", estado24);
        Cidade cidade34 = gerarCidade("Belmonte", estado24);
        Cidade cidade35 = gerarCidade("Benedito Novo", estado24);
        Cidade cidade36 = gerarCidade("Biguacu", estado24);
        Cidade cidade37 = gerarCidade("Blumenau", estado24);
        Cidade cidade38 = gerarCidade("Bocaina do Sul", estado24);
        Cidade cidade39 = gerarCidade("Bom Jardim da Serra", estado24);
        Cidade cidade40 = gerarCidade("Bom Jesus do Oeste", estado24);
        Cidade cidade41 = gerarCidade("Bom Jesus", estado24);
        Cidade cidade42 = gerarCidade("Bom Retiro", estado24);
        Cidade cidade43 = gerarCidade("Bombinhas", estado24);
        Cidade cidade44 = gerarCidade("Botuvera", estado24);
        Cidade cidade45 = gerarCidade("Braco do Norte", estado24);
        Cidade cidade46 = gerarCidade("Braco do Trombudo", estado24);
        Cidade cidade47 = gerarCidade("Brunopolis", estado24);
        Cidade cidade48 = gerarCidade("Brusque", estado24);
        Cidade cidade49 = gerarCidade("Cacador", estado24);
        Cidade cidade50 = gerarCidade("Caibi", estado24);
        Cidade cidade51 = gerarCidade("Calmon", estado24);
        Cidade cidade52 = gerarCidade("Camboriu", estado24);
        Cidade cidade53 = gerarCidade("Campo Alegre", estado24);
        Cidade cidade54 = gerarCidade("Campo Belo do Sul", estado24);
        Cidade cidade55 = gerarCidade("Campo Ere", estado24);
        Cidade cidade56 = gerarCidade("Campos Novos", estado24);
        Cidade cidade57 = gerarCidade("Canelinha", estado24);
        Cidade cidade58 = gerarCidade("Canoinhas", estado24);
        Cidade cidade59 = gerarCidade("Capao Alto", estado24);
        Cidade cidade60 = gerarCidade("Capinzal", estado24);
        Cidade cidade61 = gerarCidade("Capivari de Baixo", estado24);
        Cidade cidade62 = gerarCidade("Catanduvas", estado24);
        Cidade cidade63 = gerarCidade("Caxambu do Sul", estado24);
        Cidade cidade64 = gerarCidade("Celso Ramos", estado24);
        Cidade cidade65 = gerarCidade("Cerro Negro", estado24);
        Cidade cidade66 = gerarCidade("Chapadao do Lageado", estado24);
        Cidade cidade67 = gerarCidade("Chapeco", estado24);
        Cidade cidade68 = gerarCidade("Cocal do Sul", estado24);
        Cidade cidade69 = gerarCidade("Concordia", estado24);
        Cidade cidade70 = gerarCidade("Cordilheira Alta", estado24);
        Cidade cidade72 = gerarCidade("Coronel Freitas", estado24);
        Cidade cidade73 = gerarCidade("Coronel Martins", estado24);
        Cidade cidade74 = gerarCidade("Correia Pinto", estado24);
        Cidade cidade75 = gerarCidade("Corupa", estado24);
        Cidade cidade76 = gerarCidade("Criciuma", estado24);
        Cidade cidade77 = gerarCidade("Cunha Pora", estado24);
        Cidade cidade78 = gerarCidade("Cunhatai", estado24);
        Cidade cidade79 = gerarCidade("Curitibanos", estado24);
        Cidade cidade80 = gerarCidade("Descanso", estado24);
        Cidade cidade81 = gerarCidade("Dionisio Cerqueira", estado24);
        Cidade cidade82 = gerarCidade("Dona Emma", estado24);
        Cidade cidade83 = gerarCidade("Doutor Pedrinho", estado24);
        Cidade cidade84 = gerarCidade("Entre Rios", estado24);
        Cidade cidade85 = gerarCidade("Ermo", estado24);
        Cidade cidade86 = gerarCidade("Erval Velho", estado24);
        Cidade cidade87 = gerarCidade("Faxinal dos Guedes", estado24);
        Cidade cidade88 = gerarCidade("Flor do Sertao", estado24);
        Cidade cidade89 = gerarCidade("Florianopolis", estado24);
        Cidade cidade90 = gerarCidade("Formosa do Sul", estado24);
        Cidade cidade91 = gerarCidade("Forquilhinha", estado24);
        Cidade cidade92 = gerarCidade("Fraiburgo", estado24);
        Cidade cidade93 = gerarCidade("Frei Rogerio", estado24);
        Cidade cidade94 = gerarCidade("Galvao", estado24);
        Cidade cidade95 = gerarCidade("Garopaba", estado24);
        Cidade cidade96 = gerarCidade("Garopaba", estado24);
        Cidade cidade97 = gerarCidade("Gaspar", estado24);
        Cidade cidade98 = gerarCidade("Governador Celso Ramos", estado24);
        Cidade cidade99 = gerarCidade("Governador Celso Ramos", estado24);
        Cidade cidade100 = gerarCidade("Gravatal", estado24);
        Cidade cidade101 = gerarCidade("Guabiruba", estado24);
        Cidade cidade102 = gerarCidade("Guaraciaba", estado24);
        Cidade cidade103 = gerarCidade("Guaramirim", estado24);
        Cidade cidade104 = gerarCidade("Guaruja do Sul", estado24);
        Cidade cidade105 = gerarCidade("Guatambu", estado24);
        Cidade cidade106 = gerarCidade("Herval d'Oeste", estado24);
        Cidade cidade107 = gerarCidade("Ibiam", estado24);
        Cidade cidade108 = gerarCidade("Ibicare", estado24);
        Cidade cidade109 = gerarCidade("Ibirama", estado24);
        Cidade cidade110 = gerarCidade("Icara", estado24);
        Cidade cidade111 = gerarCidade("Ilhota", estado24);
        Cidade cidade112 = gerarCidade("Imarui", estado24);
        Cidade cidade113 = gerarCidade("Imbituba", estado24);
        Cidade cidade114 = gerarCidade("Imbuia", estado24);
        Cidade cidade115 = gerarCidade("Indaial", estado24);
        Cidade cidade116 = gerarCidade("Iomere", estado24);
        Cidade cidade117 = gerarCidade("Ipira", estado24);
        Cidade cidade118 = gerarCidade("Ipora do Oeste", estado24);
        Cidade cidade119 = gerarCidade("Ipuacu", estado24);
        Cidade cidade120 = gerarCidade("Ipumirim", estado24);
        Cidade cidade121 = gerarCidade("Iraceminha", estado24);
        Cidade cidade122 = gerarCidade("Irani", estado24);
        Cidade cidade123 = gerarCidade("Irati", estado24);
        Cidade cidade124 = gerarCidade("Irineopolis", estado24);
        Cidade cidade125 = gerarCidade("Ita", estado24);
        Cidade cidade126 = gerarCidade("Itaiopolis", estado24);
        Cidade cidade127 = gerarCidade("Itajai", estado24);
        Cidade cidade128 = gerarCidade("Itapema", estado24);
        Cidade cidade129 = gerarCidade("Itapiranga", estado24);
        Cidade cidade130 = gerarCidade("Itapoa", estado24);
        Cidade cidade131 = gerarCidade("Ituporanga", estado24);
        Cidade cidade132 = gerarCidade("Jabora", estado24);
        Cidade cidade133 = gerarCidade("Jacinto Machado", estado24);
        Cidade cidade134 = gerarCidade("Jaguaruna", estado24);
        Cidade cidade135 = gerarCidade("Jaragua do Sul", estado24);
        Cidade cidade136 = gerarCidade("Jardinopolis", estado24);
        Cidade cidade137 = gerarCidade("Joacaba", estado24);
        Cidade cidade138 = gerarCidade("Joinville", estado24);
        Cidade cidade139 = gerarCidade("Jose Boiteux", estado24);
        Cidade cidade140 = gerarCidade("Jupia", estado24);
        Cidade cidade141 = gerarCidade("Lacerdopolis", estado24);
        Cidade cidade142 = gerarCidade("Lages", estado24);
        Cidade cidade143 = gerarCidade("Laguna", estado24);
        Cidade cidade144 = gerarCidade("Lajeado Grande", estado24);
        Cidade cidade145 = gerarCidade("Laurentino", estado24);
        Cidade cidade146 = gerarCidade("Lauro Muller", estado24);
        Cidade cidade147 = gerarCidade("Lebon Regis", estado24);
        Cidade cidade148 = gerarCidade("Leoberto Leal", estado24);
        Cidade cidade149 = gerarCidade("Lindoia do Sul", estado24);
        Cidade cidade150 = gerarCidade("Lontras", estado24);
        Cidade cidade151 = gerarCidade("Luiz Alves", estado24);
        Cidade cidade152 = gerarCidade("Luzerna", estado24);
        Cidade cidade153 = gerarCidade("Macieira", estado24);
        Cidade cidade154 = gerarCidade("Mafra", estado24);
        Cidade cidade155 = gerarCidade("Major Gercino", estado24);
        Cidade cidade156 = gerarCidade("Major Vieira", estado24);
        Cidade cidade157 = gerarCidade("Maracaja", estado24);
        Cidade cidade158 = gerarCidade("Maravilha", estado24);
        Cidade cidade159 = gerarCidade("Marema", estado24);
        Cidade cidade160 = gerarCidade("Massaranduba", estado24);
        Cidade cidade161 = gerarCidade("Matos Costa", estado24);
        Cidade cidade162 = gerarCidade("Meleiro", estado24);
        Cidade cidade163 = gerarCidade("Mirim Doce", estado24);
        Cidade cidade164 = gerarCidade("Modelo", estado24);
        Cidade cidade165 = gerarCidade("Mondai", estado24);
        Cidade cidade166 = gerarCidade("Monte Carlo", estado24);
        Cidade cidade167 = gerarCidade("Monte Castelo", estado24);
        Cidade cidade168 = gerarCidade("Morro Grande", estado24);
        Cidade cidade169 = gerarCidade("Morro da Fumaca", estado24);
        Cidade cidade170 = gerarCidade("Navegantes", estado24);
        Cidade cidade171 = gerarCidade("Nova Erechim", estado24);
        Cidade cidade172 = gerarCidade("Nova Itaberaba", estado24);
        Cidade cidade173 = gerarCidade("Nova Trento", estado24);
        Cidade cidade174 = gerarCidade("Nova Veneza", estado24);
        Cidade cidade175 = gerarCidade("Novo Horizonte", estado24);
        Cidade cidade176 = gerarCidade("Orleans", estado24);
        Cidade cidade177 = gerarCidade("Otacilio Costa", estado24);
        Cidade cidade178 = gerarCidade("Ouro Verde", estado24);
        Cidade cidade179 = gerarCidade("Ouro", estado24);
        Cidade cidade180 = gerarCidade("Paial", estado24);
        Cidade cidade181 = gerarCidade("Painel", estado24);
        Cidade cidade182 = gerarCidade("Palhoca", estado24);
        Cidade cidade183 = gerarCidade("Palma Sola", estado24);
        Cidade cidade184 = gerarCidade("Palmeira", estado24);
        Cidade cidade185 = gerarCidade("Palmitos", estado24);
        Cidade cidade186 = gerarCidade("Papanduva", estado24);
        Cidade cidade187 = gerarCidade("Paraiso", estado24);
        Cidade cidade188 = gerarCidade("Passo de Torres", estado24);
        Cidade cidade189 = gerarCidade("Passos Maia", estado24);
        Cidade cidade190 = gerarCidade("Paulo Lopes", estado24);
        Cidade cidade191 = gerarCidade("Pedras Grandes", estado24);
        Cidade cidade192 = gerarCidade("Penha", estado24);
        Cidade cidade193 = gerarCidade("Peritiba", estado24);
        Cidade cidade194 = gerarCidade("Petrolandia", estado24);
        Cidade cidade195 = gerarCidade("Picarras", estado24);
        Cidade cidade196 = gerarCidade("Pinhalzinho", estado24);
        Cidade cidade197 = gerarCidade("Pinheiro Preto", estado24);
        Cidade cidade198 = gerarCidade("Piratuba", estado24);
        Cidade cidade199 = gerarCidade("Planalto Alegre", estado24);
        Cidade cidade200 = gerarCidade("Pomerode", estado24);
        Cidade cidade201 = gerarCidade("Ponte Alta do Norte", estado24);
        Cidade cidade202 = gerarCidade("Ponte Alta", estado24);
        Cidade cidade203 = gerarCidade("Ponte Serrada", estado24);
        Cidade cidade204 = gerarCidade("Porto Belo", estado24);
        Cidade cidade205 = gerarCidade("Porto Uniao", estado24);
        Cidade cidade206 = gerarCidade("Pouso Redondo", estado24);
        Cidade cidade207 = gerarCidade("Praia Grande", estado24);
        Cidade cidade208 = gerarCidade("Presidente Castelo Branco", estado24);
        Cidade cidade209 = gerarCidade("Presidente Getulio", estado24);
        Cidade cidade210 = gerarCidade("Presidente Nereu", estado24);
        Cidade cidade211 = gerarCidade("Princesa", estado24);
        Cidade cidade212 = gerarCidade("Quilombo", estado24);
        Cidade cidade213 = gerarCidade("Rancho Queimado", estado24);
        Cidade cidade214 = gerarCidade("Rio Fortuna", estado24);
        Cidade cidade215 = gerarCidade("Rio Negrinho", estado24);
        Cidade cidade216 = gerarCidade("Rio Rufino", estado24);
        Cidade cidade217 = gerarCidade("Rio d'Oeste", estado24);
        Cidade cidade218 = gerarCidade("Rio das Antas", estado24);
        Cidade cidade219 = gerarCidade("Rio do Campo", estado24);
        Cidade cidade220 = gerarCidade("Rio do Sul", estado24);
        Cidade cidade221 = gerarCidade("Rio dos Cedros", estado24);
        Cidade cidade222 = gerarCidade("Riqueza", estado24);
        Cidade cidade223 = gerarCidade("Romelandia", estado24);
        Cidade cidade224 = gerarCidade("Salete", estado24);
        Cidade cidade225 = gerarCidade("Saltinho", estado24);
        Cidade cidade226 = gerarCidade("Salto Veloso", estado24);
        Cidade cidade227 = gerarCidade("Sangao", estado24);
        Cidade cidade228 = gerarCidade("Santa Cecilia", estado24);
        Cidade cidade229 = gerarCidade("Santa Helena", estado24);
        Cidade cidade230 = gerarCidade("Santa Rosa de Lima", estado24);
        Cidade cidade231 = gerarCidade("Santa Rosa do Sul", estado24);
        Cidade cidade232 = gerarCidade("Santa Terezinha do Progresso", estado24);
        Cidade cidade233 = gerarCidade("Santa Terezinha", estado24);
        Cidade cidade234 = gerarCidade("Santiago do Sul", estado24);
        Cidade cidade235 = gerarCidade("Santo Amaro da Imperatriz", estado24);
        Cidade cidade236 = gerarCidade("Sao Bento do Sul", estado24);
        Cidade cidade237 = gerarCidade("Sao Bernardino", estado24);
        Cidade cidade238 = gerarCidade("Sao Bonifacio", estado24);
        Cidade cidade239 = gerarCidade("Sao Carlos", estado24);
        Cidade cidade240 = gerarCidade("Sao Cristovao do Sul", estado24);
        Cidade cidade241 = gerarCidade("Sao Domingos", estado24);
        Cidade cidade242 = gerarCidade("Sao Francisco do Sul", estado24);
        Cidade cidade243 = gerarCidade("Sao Joao Batista", estado24);
        Cidade cidade245 = gerarCidade("Sao Joao do Itaperiu", estado24);
        Cidade cidade246 = gerarCidade("Sao Joao do Oeste", estado24);
        Cidade cidade247 = gerarCidade("Sao Joao do Sul", estado24);
        Cidade cidade248 = gerarCidade("Sao Joaquim", estado24);
        Cidade cidade249 = gerarCidade("Sao Jose do Cedro", estado24);
        Cidade cidade250 = gerarCidade("Sao Jose do Cerrito", estado24);
        Cidade cidade251 = gerarCidade("Sao Jose", estado24);
        Cidade cidade252 = gerarCidade("Sao Lourenco d'Oeste", estado24);
        Cidade cidade253 = gerarCidade("Sao Ludgero", estado24);
        Cidade cidade254 = gerarCidade("Sao Martinho", estado24);
        Cidade cidade255 = gerarCidade("Sao Miguel d'Oeste", estado24);
        Cidade cidade256 = gerarCidade("Sao Miguel da Boa Vista", estado24);
        Cidade cidade257 = gerarCidade("Sao Pedro de Alcantara", estado24);
        Cidade cidade258 = gerarCidade("Saudades", estado24);
        Cidade cidade259 = gerarCidade("Schroeder", estado24);
        Cidade cidade260 = gerarCidade("Seara", estado24);
        Cidade cidade261 = gerarCidade("Serra Alta", estado24);
        Cidade cidade262 = gerarCidade("Sideropolis", estado24);
        Cidade cidade263 = gerarCidade("Sombrio", estado24);
        Cidade cidade264 = gerarCidade("Sul Brasil", estado24);
        Cidade cidade265 = gerarCidade("Taio", estado24);
        Cidade cidade266 = gerarCidade("Tangara", estado24);
        Cidade cidade267 = gerarCidade("Tigrinhos", estado24);
        Cidade cidade268 = gerarCidade("Tijucas", estado24);
        Cidade cidade269 = gerarCidade("Timbe do Sul", estado24);
        Cidade cidade270 = gerarCidade("Timbo Grande", estado24);
        Cidade cidade271 = gerarCidade("Timbo", estado24);
        Cidade cidade272 = gerarCidade("Tres Barras", estado24);
        Cidade cidade273 = gerarCidade("Treviso", estado24);
        Cidade cidade274 = gerarCidade("Treze Tilias", estado24);
        Cidade cidade275 = gerarCidade("Treze de Maio", estado24);
        Cidade cidade276 = gerarCidade("Trombudo Central", estado24);
        Cidade cidade277 = gerarCidade("Tunapolis", estado24);
        Cidade cidade278 = gerarCidade("Turvo", estado24);
        Cidade cidade279 = gerarCidade("Uniao do Oeste", estado24);
        Cidade cidade280 = gerarCidade("Urubici", estado24);
        Cidade cidade281 = gerarCidade("Urupema", estado24);
        Cidade cidade282 = gerarCidade("Urussanga", estado24);
        Cidade cidade283 = gerarCidade("Vargeao", estado24);
        Cidade cidade284 = gerarCidade("Vargem Bonita", estado24);
        Cidade cidade285 = gerarCidade("Vargem", estado24);
        Cidade cidade286 = gerarCidade("Vidal Ramos", estado24);
        Cidade cidade287 = gerarCidade("Videira", estado24);
        Cidade cidade288 = gerarCidade("Vitor Meireles", estado24);
        Cidade cidade289 = gerarCidade("Witmarsum", estado24);
        Cidade cidade290 = gerarCidade("Xanxere", estado24);
        Cidade cidade291 = gerarCidade("Xavantina", estado24);
        Cidade cidade292 = gerarCidade("Xaxim", estado24);
        Cidade cidade293 = gerarCidade("Zortea", estado24);

        Logradouro logradouro1 = gerarLogradouro("Água Verde",
                                                 "89037-395",
                                                 "Rua Adelino Pereira",
                                                 "casa",
                                                 cidade37);
        Logradouro logradouro2 = gerarLogradouro("Badenfurt",
                                                 "89072-125",
                                                 "Rua Adauto Zuave",
                                                 "casa",
                                                 cidade37);
        Logradouro logradouro3 = gerarLogradouro("Boa Vista",
                                                 "89012-240",
                                                 "Rua Adolfo Tallmann",
                                                 "casa",
                                                 cidade37);
        Logradouro logradouro4 = gerarLogradouro("Bom Retiro",
                                                 "89010-680",
                                                 "Rua Augusto Otte",
                                                 "casa",
                                                 cidade37);
        Logradouro logradouro5 = gerarLogradouro("Centro",
                                                 "89012-478",
                                                 "Rua Atalanta ",
                                                 "casa",
                                                 cidade37);
        Logradouro logradouro6 = gerarLogradouro("Escola Agrícola",
                                                 "89031-520",
                                                 "Rua Adolfo Hass ",
                                                 "casa",
                                                 cidade37);
        Logradouro logradouro7 = gerarLogradouro("Fidélis",
                                                 "89060-386",
                                                 "Rua Alfredo Mette",
                                                 "casa",
                                                 cidade37);
        Logradouro logradouro8 = gerarLogradouro("Fortaleza",
                                                 "89055-490",
                                                 "Guaporema",
                                                 "casa",
                                                 cidade37);
        Logradouro logradouro9 = gerarLogradouro("Fortaleza Alta",
                                                 "89058-254",
                                                 "Rua Ajuricaba",
                                                 "casa",
                                                 cidade37);
        Logradouro logradouro10 = gerarLogradouro("Garcia",
                                                  "89022-430",
                                                  "Rua Acre",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro11 = gerarLogradouro("Glória",
                                                  "89025-471",
                                                  "Rua Abel Dias Filho",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro12 = gerarLogradouro("Itoupava Central",
                                                  "89062-160",
                                                  "Rua Abel Zucki",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro13 = gerarLogradouro("Itoupava Norte",
                                                  "cep",
                                                  "Rua Albert Martin",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro14 = gerarLogradouro("Itoupava Seca",
                                                  "89030-090",
                                                  "Rua Alagoas",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro15 = gerarLogradouro("Itoupavazinha",
                                                  "89066-354",
                                                  "Rua Adele Wruck",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro16 = gerarLogradouro("Jardim Blumenau",
                                                  "89010-330",
                                                  "Rua Coronel Vidal Ramos",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro17 = gerarLogradouro("Nova Esperança",
                                                  "89051-365",
                                                  "Rua Alexandre Costa",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro18 = gerarLogradouro("Passo Manso",
                                                  "89032-330",
                                                  "Rua Albert Schulz",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro19 = gerarLogradouro("Ponta Aguda",
                                                  "89050-195",
                                                  "Rua Arapongas",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro20 = gerarLogradouro("Progresso",
                                                  "89027-750",
                                                  "Rua Adelina Nuss",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro21 = gerarLogradouro("Ribeirão Fresco",
                                                  "89015-070",
                                                  "Rua Boa Esperança",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro22 = gerarLogradouro("Salto",
                                                  "89031-671",
                                                  "Rua Abacate ",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro23 = gerarLogradouro("Salto do Norte",
                                                  "89070-544",
                                                  "Rua Adolpho Bressanini",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro24 = gerarLogradouro("Salto Weissbach",
                                                  "89032-243",
                                                  "Rua Alberto Augusto Ern",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro25 = gerarLogradouro("Testo Salto",
                                                  "89074-150",
                                                  "Rua Adolfo Buhr",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro26 = gerarLogradouro("Tribess",
                                                  "89057-676",
                                                  "Rua Adolfo Tribess",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro27 = gerarLogradouro("Valparaíso",
                                                  "89023-612",
                                                  "Rua Adolfo Kolping",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro28 = gerarLogradouro("Velha",
                                                  "89036-453",
                                                  "Rua Adolfo Passig",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro29 = gerarLogradouro("Velha Central",
                                                  "89046-030",
                                                  "Rua Alberto Lobe",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro30 = gerarLogradouro("Velha Grande",
                                                  "89045-442",
                                                  "Rua Artur Henkels",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro31 = gerarLogradouro("Victor Konder",
                                                  "89012-078",
                                                  "Rua Abacateiro",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro32 = gerarLogradouro("Vila Formosa",
                                                  "89023-040",
                                                  "Rua Alberto Müller",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro33 = gerarLogradouro("Vila Itoupava",
                                                  "89075-337",
                                                  "Rua Artur Sasse",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro34 = gerarLogradouro("Vila Nova",
                                                  "89035-280",
                                                  "Rua Afonso Pena",
                                                  "casa",
                                                  cidade37);
        Logradouro logradouro35 = gerarLogradouro("Vorstadt",
                                                  "89015-405",
                                                  "Rua Adolfo Schmalz",
                                                  "casa",
                                                  cidade37);

        Endereco endereco1 = gerarEndereco(pais30, estado24, cidade37, logradouro1);
        Endereco endereco2 = gerarEndereco(pais30, estado24, cidade37, logradouro2);
        Endereco endereco3 = gerarEndereco(pais30, estado24, cidade37, logradouro3);
        Endereco endereco4 = gerarEndereco(pais30, estado24, cidade37, logradouro4);
        Endereco endereco5 = gerarEndereco(pais30, estado24, cidade37, logradouro5);

        Usuario gerente = gerarUsuario("Gerente",
                                       "da Silva",
                                       "gerente@webbakery.com",
                                       "gerente",
                                       TipoUsuario.GERENTE);

        Usuario cliente = gerarUsuario("TOCliente",
                                       "da Silva",
                                       "cliente@gmail.com",
                                       "cliente",
                                       TipoUsuario.CLIENTE);

        Usuario admEstoque = gerarUsuario("Administrador de Estoque",
                                          "da Silva",
                                          "admEstoque@webbakery.com",
                                          "admEstoque",
                                          TipoUsuario.ADMINISTRADOR_ESTOQUE);

        Usuario caixa = gerarUsuario("Caixa",
                                     "da Silva",
                                     "caixa@webbakery.com",
                                     "caixa",
                                     TipoUsuario.CAIXA);

        Usuario padeiro = gerarUsuario("Padeiro",
                                       "da Silva",
                                       "padeiro@webbakery.com",
                                       "padeiro",
                                       TipoUsuario.PADEIRO);

        Funcionario fGerente = gerarFuncionario(BigDecimal.valueOf(3000.00),
                                                endereco1,
                                                gerente,
                                                new Date(),
                                                "111.666.444-88",
                                                "5.684.348",
                                                "(55) 658847895");
        Funcionario fAdmEstoque = gerarFuncionario(BigDecimal.valueOf(2500.00),
                                                   endereco2,
                                                   admEstoque,
                                                   new Date(),
                                                   "777.345.444-88",
                                                   "5.909.348",
                                                   "(55) 689089895");
        Funcionario fPadeiro = gerarFuncionario(BigDecimal.valueOf(1500.00),
                                                endereco3,
                                                padeiro,
                                                new Date(),
                                                "111.533.494-00",
                                                "5.684.300",
                                                "(55) 658810105");
        Funcionario fCaixa = gerarFuncionario(BigDecimal.valueOf(1700.00),
                                              endereco4,
                                              caixa,
                                              new Date(),
                                              "111.555.1425-88",
                                              "5.444.348",
                                              "(55) 655555895");

        Cliente cCliente = gerarCliente("666.533.494-00",
                                        "(55) 656666896",
                                        new Date(),
                                        cliente,
                                        endereco5);

        em.persist(gerente);
        em.persist(cliente);
        em.persist(padeiro);
        em.persist(admEstoque);
        em.persist(caixa);

        em.persist(pais1);
        em.persist(pais2);
        em.persist(pais3);
        em.persist(pais4);
        em.persist(pais5);
        em.persist(pais6);
        em.persist(pais7);
        em.persist(pais8);
        em.persist(pais9);
        em.persist(pais10);
        em.persist(pais11);
        em.persist(pais12);
        em.persist(pais13);
        em.persist(pais14);
        em.persist(pais15);
        em.persist(pais16);
        em.persist(pais17);
        em.persist(pais18);
        em.persist(pais19);
        em.persist(pais20);
        em.persist(pais21);
        em.persist(pais22);
        em.persist(pais23);
        em.persist(pais24);
        em.persist(pais25);
        em.persist(pais26);
        em.persist(pais27);
        em.persist(pais28);
        em.persist(pais29);
        em.persist(pais30);
        em.persist(pais31);
        em.persist(pais32);
        em.persist(pais33);
        em.persist(pais34);
        em.persist(pais35);
        em.persist(pais36);
        em.persist(pais37);
        em.persist(pais38);
        em.persist(pais39);
        em.persist(pais40);
        em.persist(pais41);
        em.persist(pais42);
        em.persist(pais43);
        em.persist(pais44);
        em.persist(pais45);
        em.persist(pais46);
        em.persist(pais47);
        em.persist(pais48);
        em.persist(pais49);
        em.persist(pais50);
        em.persist(pais51);
        em.persist(pais52);
        em.persist(pais53);
        em.persist(pais54);
        em.persist(pais55);
        em.persist(pais56);
        em.persist(pais57);
        em.persist(pais58);
        em.persist(pais59);
        em.persist(pais60);
        em.persist(pais61);
        em.persist(pais62);
        em.persist(pais63);
        em.persist(pais64);
        em.persist(pais65);
        em.persist(pais66);
        em.persist(pais67);
        em.persist(pais68);
        em.persist(pais69);
        em.persist(pais70);
        em.persist(pais71);
        em.persist(pais72);
        em.persist(pais73);
        em.persist(pais74);
        em.persist(pais75);
        em.persist(pais76);
        em.persist(pais77);
        em.persist(pais78);
        em.persist(pais79);
        em.persist(pais80);
        em.persist(pais81);
        em.persist(pais82);
        em.persist(pais83);
        em.persist(pais84);
        em.persist(pais85);
        em.persist(pais86);
        em.persist(pais87);
        em.persist(pais88);
        em.persist(pais89);
        em.persist(pais90);
        em.persist(pais91);
        em.persist(pais92);
        em.persist(pais93);
        em.persist(pais94);
        em.persist(pais95);
        em.persist(pais95);
        em.persist(pais96);
        em.persist(pais97);
        em.persist(pais98);
        em.persist(pais99);
        em.persist(pais100);
        em.persist(pais101);
        em.persist(pais102);
        em.persist(pais103);
        em.persist(pais104);
        em.persist(pais105);
        em.persist(pais106);
        em.persist(pais107);
        em.persist(pais108);
        em.persist(pais109);
        em.persist(pais110);
        em.persist(pais111);
        em.persist(pais112);
        em.persist(pais113);
        em.persist(pais114);
        em.persist(pais115);
        em.persist(pais116);
        em.persist(pais117);
        em.persist(pais118);
        em.persist(pais119);
        em.persist(pais120);
        em.persist(pais121);
        em.persist(pais122);
        em.persist(pais123);
        em.persist(pais124);
        em.persist(pais125);
        em.persist(pais126);
        em.persist(pais127);
        em.persist(pais128);
        em.persist(pais129);
        em.persist(pais130);
        em.persist(pais131);
        em.persist(pais132);
        em.persist(pais133);
        em.persist(pais134);
        em.persist(pais135);
        em.persist(pais136);
        em.persist(pais137);
        em.persist(pais138);
        em.persist(pais139);
        em.persist(pais140);
        em.persist(pais141);
        em.persist(pais142);
        em.persist(pais143);
        em.persist(pais144);
        em.persist(pais145);
        em.persist(pais146);
        em.persist(pais147);
        em.persist(pais148);
        em.persist(pais149);
        em.persist(pais150);
        em.persist(pais151);
        em.persist(pais152);
        em.persist(pais153);
        em.persist(pais154);
        em.persist(pais155);
        em.persist(pais156);
        em.persist(pais157);
        em.persist(pais158);
        em.persist(pais159);
        em.persist(pais160);
        em.persist(pais161);
        em.persist(pais162);
        em.persist(pais163);
        em.persist(pais164);
        em.persist(pais165);
        em.persist(pais166);
        em.persist(pais167);
        em.persist(pais168);
        em.persist(pais169);
        em.persist(pais170);
        em.persist(pais171);
        em.persist(pais172);
        em.persist(pais173);
        em.persist(pais174);
        em.persist(pais175);
        em.persist(pais176);
        em.persist(pais177);
        em.persist(pais178);
        em.persist(pais179);
        em.persist(pais180);
        em.persist(pais181);
        em.persist(pais182);
        em.persist(pais183);
        em.persist(pais184);
        em.persist(pais185);
        em.persist(pais186);
        em.persist(pais187);

        em.persist(estado1);
        em.persist(estado2);
        em.persist(estado3);
        em.persist(estado4);
        em.persist(estado5);
        em.persist(estado6);
        em.persist(estado7);
        em.persist(estado8);
        em.persist(estado9);
        em.persist(estado10);
        em.persist(estado11);
        em.persist(estado12);
        em.persist(estado13);
        em.persist(estado14);
        em.persist(estado15);
        em.persist(estado16);
        em.persist(estado17);
        em.persist(estado18);
        em.persist(estado19);
        em.persist(estado20);
        em.persist(estado21);
        em.persist(estado22);
        em.persist(estado23);
        em.persist(estado24);
        em.persist(estado25);
        em.persist(estado26);
        em.persist(estado27);

        em.persist(cidade1);
        em.persist(cidade2);
        em.persist(cidade3);
        em.persist(cidade4);
        em.persist(cidade5);
        em.persist(cidade6);
        em.persist(cidade7);
        em.persist(cidade8);
        em.persist(cidade9);
        em.persist(cidade10);
        em.persist(cidade11);
        em.persist(cidade12);
        em.persist(cidade13);
        em.persist(cidade14);
        em.persist(cidade15);
        em.persist(cidade16);
        em.persist(cidade17);
        em.persist(cidade18);
        em.persist(cidade19);
        em.persist(cidade20);
        em.persist(cidade21);
        em.persist(cidade22);
        em.persist(cidade23);
        em.persist(cidade24);
        em.persist(cidade25);
        em.persist(cidade26);
        em.persist(cidade27);
        em.persist(cidade28);
        em.persist(cidade29);
        em.persist(cidade30);
        em.persist(cidade31);
        em.persist(cidade32);
        em.persist(cidade33);
        em.persist(cidade34);
        em.persist(cidade35);
        em.persist(cidade36);
        em.persist(cidade37);
        em.persist(cidade38);
        em.persist(cidade39);
        em.persist(cidade40);
        em.persist(cidade41);
        em.persist(cidade42);
        em.persist(cidade43);
        em.persist(cidade44);
        em.persist(cidade45);
        em.persist(cidade46);
        em.persist(cidade47);
        em.persist(cidade48);
        em.persist(cidade49);
        em.persist(cidade50);
        em.persist(cidade51);
        em.persist(cidade52);
        em.persist(cidade53);
        em.persist(cidade54);
        em.persist(cidade55);
        em.persist(cidade56);
        em.persist(cidade57);
        em.persist(cidade58);
        em.persist(cidade59);
        em.persist(cidade60);
        em.persist(cidade61);
        em.persist(cidade61);
        em.persist(cidade62);
        em.persist(cidade63);
        em.persist(cidade64);
        em.persist(cidade65);
        em.persist(cidade66);
        em.persist(cidade67);
        em.persist(cidade68);
        em.persist(cidade69);
        em.persist(cidade70);
        em.persist(cidade72);
        em.persist(cidade73);
        em.persist(cidade74);
        em.persist(cidade75);
        em.persist(cidade76);
        em.persist(cidade77);
        em.persist(cidade78);
        em.persist(cidade79);
        em.persist(cidade80);
        em.persist(cidade81);
        em.persist(cidade82);
        em.persist(cidade83);
        em.persist(cidade84);
        em.persist(cidade85);
        em.persist(cidade86);
        em.persist(cidade87);
        em.persist(cidade88);
        em.persist(cidade89);
        em.persist(cidade90);
        em.persist(cidade91);
        em.persist(cidade92);
        em.persist(cidade93);
        em.persist(cidade94);
        em.persist(cidade95);
        em.persist(cidade96);
        em.persist(cidade97);
        em.persist(cidade98);
        em.persist(cidade99);
        em.persist(cidade100);
        em.persist(cidade101);
        em.persist(cidade102);
        em.persist(cidade103);
        em.persist(cidade104);
        em.persist(cidade105);
        em.persist(cidade106);
        em.persist(cidade107);
        em.persist(cidade108);
        em.persist(cidade109);
        em.persist(cidade110);
        em.persist(cidade111);
        em.persist(cidade112);
        em.persist(cidade113);
        em.persist(cidade114);
        em.persist(cidade115);
        em.persist(cidade116);
        em.persist(cidade117);
        em.persist(cidade118);
        em.persist(cidade119);
        em.persist(cidade120);
        em.persist(cidade121);
        em.persist(cidade122);
        em.persist(cidade123);
        em.persist(cidade124);
        em.persist(cidade125);
        em.persist(cidade126);
        em.persist(cidade127);
        em.persist(cidade128);
        em.persist(cidade129);
        em.persist(cidade130);
        em.persist(cidade131);
        em.persist(cidade132);
        em.persist(cidade133);
        em.persist(cidade134);
        em.persist(cidade135);
        em.persist(cidade136);
        em.persist(cidade137);
        em.persist(cidade138);
        em.persist(cidade139);
        em.persist(cidade140);
        em.persist(cidade141);
        em.persist(cidade142);
        em.persist(cidade143);
        em.persist(cidade144);
        em.persist(cidade145);
        em.persist(cidade146);
        em.persist(cidade147);
        em.persist(cidade148);
        em.persist(cidade149);
        em.persist(cidade150);
        em.persist(cidade151);
        em.persist(cidade152);
        em.persist(cidade153);
        em.persist(cidade154);
        em.persist(cidade155);
        em.persist(cidade156);
        em.persist(cidade157);
        em.persist(cidade158);
        em.persist(cidade159);
        em.persist(cidade160);
        em.persist(cidade161);
        em.persist(cidade162);
        em.persist(cidade163);
        em.persist(cidade164);
        em.persist(cidade165);
        em.persist(cidade166);
        em.persist(cidade167);
        em.persist(cidade168);
        em.persist(cidade169);
        em.persist(cidade170);
        em.persist(cidade171);
        em.persist(cidade172);
        em.persist(cidade173);
        em.persist(cidade174);
        em.persist(cidade175);
        em.persist(cidade176);
        em.persist(cidade177);
        em.persist(cidade178);
        em.persist(cidade179);
        em.persist(cidade180);
        em.persist(cidade181);
        em.persist(cidade182);
        em.persist(cidade183);
        em.persist(cidade184);
        em.persist(cidade185);
        em.persist(cidade186);
        em.persist(cidade187);
        em.persist(cidade188);
        em.persist(cidade189);
        em.persist(cidade190);
        em.persist(cidade191);
        em.persist(cidade192);
        em.persist(cidade193);
        em.persist(cidade194);
        em.persist(cidade195);
        em.persist(cidade196);
        em.persist(cidade197);
        em.persist(cidade198);
        em.persist(cidade199);
        em.persist(cidade200);
        em.persist(cidade201);
        em.persist(cidade202);
        em.persist(cidade203);
        em.persist(cidade204);
        em.persist(cidade205);
        em.persist(cidade206);
        em.persist(cidade207);
        em.persist(cidade208);
        em.persist(cidade209);
        em.persist(cidade210);
        em.persist(cidade211);
        em.persist(cidade212);
        em.persist(cidade213);
        em.persist(cidade214);
        em.persist(cidade215);
        em.persist(cidade216);
        em.persist(cidade217);
        em.persist(cidade218);
        em.persist(cidade219);
        em.persist(cidade220);
        em.persist(cidade221);
        em.persist(cidade222);
        em.persist(cidade223);
        em.persist(cidade224);
        em.persist(cidade225);
        em.persist(cidade226);
        em.persist(cidade227);
        em.persist(cidade228);
        em.persist(cidade229);
        em.persist(cidade230);
        em.persist(cidade231);
        em.persist(cidade232);
        em.persist(cidade233);
        em.persist(cidade234);
        em.persist(cidade235);
        em.persist(cidade236);
        em.persist(cidade237);
        em.persist(cidade238);
        em.persist(cidade239);
        em.persist(cidade240);
        em.persist(cidade241);
        em.persist(cidade242);
        em.persist(cidade243);
        em.persist(cidade245);
        em.persist(cidade246);
        em.persist(cidade247);
        em.persist(cidade248);
        em.persist(cidade249);
        em.persist(cidade250);
        em.persist(cidade251);
        em.persist(cidade252);
        em.persist(cidade253);
        em.persist(cidade254);
        em.persist(cidade255);
        em.persist(cidade256);
        em.persist(cidade257);
        em.persist(cidade258);
        em.persist(cidade259);
        em.persist(cidade260);
        em.persist(cidade261);
        em.persist(cidade262);
        em.persist(cidade263);
        em.persist(cidade264);
        em.persist(cidade265);
        em.persist(cidade266);
        em.persist(cidade267);
        em.persist(cidade268);
        em.persist(cidade269);
        em.persist(cidade270);
        em.persist(cidade271);
        em.persist(cidade272);
        em.persist(cidade273);
        em.persist(cidade274);
        em.persist(cidade275);
        em.persist(cidade276);
        em.persist(cidade277);
        em.persist(cidade278);
        em.persist(cidade279);
        em.persist(cidade280);
        em.persist(cidade281);
        em.persist(cidade282);
        em.persist(cidade283);
        em.persist(cidade284);
        em.persist(cidade285);
        em.persist(cidade286);
        em.persist(cidade287);
        em.persist(cidade288);
        em.persist(cidade289);
        em.persist(cidade290);
        em.persist(cidade291);
        em.persist(cidade292);
        em.persist(cidade293);

        em.persist(logradouro1);
        em.persist(logradouro2);
        em.persist(logradouro3);
        em.persist(logradouro4);
        em.persist(logradouro5);
        em.persist(logradouro6);
        em.persist(logradouro7);
        em.persist(logradouro8);
        em.persist(logradouro9);
        em.persist(logradouro10);
        em.persist(logradouro11);
        em.persist(logradouro12);
        em.persist(logradouro13);
        em.persist(logradouro14);
        em.persist(logradouro15);
        em.persist(logradouro16);
        em.persist(logradouro17);
        em.persist(logradouro18);
        em.persist(logradouro19);
        em.persist(logradouro20);
        em.persist(logradouro21);
        em.persist(logradouro22);
        em.persist(logradouro23);
        em.persist(logradouro24);
        em.persist(logradouro25);
        em.persist(logradouro26);
        em.persist(logradouro27);
        em.persist(logradouro28);
        em.persist(logradouro29);
        em.persist(logradouro30);
        em.persist(logradouro31);
        em.persist(logradouro32);
        em.persist(logradouro33);
        em.persist(logradouro34);
        em.persist(logradouro35);

        em.persist(endereco1);
        em.persist(endereco2);
        em.persist(endereco3);
        em.persist(endereco4);
        em.persist(endereco5);

        em.persist(fCaixa);
        em.persist(fPadeiro);
        em.persist(fAdmEstoque);
        em.persist(fGerente);

        em.persist(cCliente);

    }

    private static Usuario gerarUsuario(String nome,
                                        String sobrenome,
                                        String email,
                                        String senha,
                                        TipoUsuario tipo) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setSobrenome(sobrenome);
        usuario.setEmail(email);
        usuario.setSenha(Hash_Util.generateHash(senha, HashTypeEnum.SAH1));
        usuario.setTipo(tipo);
        usuario.setAtivo(true);
        return usuario;
    }

    private static Pais gerarPais(String nome, String sigla) {
        Pais pais = new Pais();
        pais.setNome(nome);
        pais.setSigla(sigla);
        pais.setAtivo(true);
        return pais;
    }

    private static Estado gerarEstado(String nome, String sigla, Pais pais) {
        Estado estado = new Estado();
        estado.setNome(nome);
        estado.setSigla(sigla);
        estado.setPais(pais);
        estado.setAtivo(true);
        return estado;
    }

    private static Cidade gerarCidade(String nome, Estado estado) {
        Cidade cidade = new Cidade();
        cidade.setEstado(estado);
        cidade.setNome(nome);
        cidade.setAtivo(true);
        return cidade;
    }

    private static Logradouro gerarLogradouro(String bairro,
                                              String cep,
                                              String rua,
                                              String complemento,
                                              Cidade cidade) {
        Logradouro logradouro = new Logradouro();
        logradouro.setBairro(bairro);
        logradouro.setCep(cep);
        logradouro.setCidade(cidade);
        logradouro.setComplemento(complemento);
        logradouro.setRua(rua);
        logradouro.setAtivo(true);
        return logradouro;
    }

    private static Funcionario gerarFuncionario(BigDecimal salario,
                                                Endereco endereco,
                                                Usuario usuario,
                                                Date dataNascimento,
                                                String cpf,
                                                String rg,
                                                String telefone) {

        Funcionario f = new Funcionario();
        f.setAtivo(true);
        f.setCpf(cpf);
        f.setDataNascimento(dataNascimento);
        f.setEndereco(endereco);
        f.setRg(rg);
        f.setSalario(salario);
        f.setTelefone(telefone);
        f.setUsuario(usuario);

        return f;
    }

    private static Cliente gerarCliente(String cpf,
                                        String telefone,
                                        Date dataNascimento,
                                        Usuario usuario,
                                        Endereco endereco) {

        Cliente c = new Cliente();
        c.setAtivo(true);
        c.setCpf(cpf);
        c.setDataNascimento(dataNascimento);
        c.setEndereco(endereco);
        c.setTelefone(telefone);
        c.setUsuario(usuario);

        return c;
    }

    private static Endereco gerarEndereco(Pais pais,
                                          Estado estado,
                                          Cidade cidade,
                                          Logradouro logradouro) {
        Endereco e = new Endereco();
        e.setAtivo(true);
        e.setPais(pais);
        e.setEstado(estado);
        e.setCidade(cidade);
        e.setLogradouro(logradouro);

        return e;
    }
}
