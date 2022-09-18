### Trabalho COMPUTAÇÃO - IME 2022 - Al PINAFI 20044 
# Tabela de conteúdos
<!--ts-->
   * [Sobre](#Sobre)
   * [Funcionamento](#Funcionamento)
   * [Organização](#Organização)
      *    [AppClass](#App)
      *    [Gui](#Gui)
      *    [Airports](#Airports)
      *    [Brain](#Brain)
      *    [Dijkstra](#Dijkstra)
      *    [Distance](#Distance)
      *    [GetDataBaseConnection](#GetDataBaseConnection)
      *    [GetDataBaseInfos](#GetDataBaseInfos)
   * Banco de Dados
<!--te-->

# Sobre 
Esse trabalho utiliza o algoritmo de dijkstra para calcular a menor rota entre dois aeroportos. Aqui, optei por criar um banco de dados que oferece os caminhos reais que cada aeroporto pode oferecer. Dessa forma, poderemos ter um número variável (e inclusive não ter escala alguma) entre dois aeroportos.

# Funcionamento
GIF monstrando o programa em funcionamento. Para facilitar o acesso, criei uma GUI onde o usuário pode escolher o estado de origem e o estado final. A partir desses inputs, o programa é capaz de decidir quais aeroportos estarão disponíveis para o usuário. Note que, quando a origem e o destino são identicos (isso é, mesmos estados e mesmos aeroportos) o botão de calcular rota é desabilitado. 

<h1 align="center">
  <img alt="GIFdemo" title="#GIFdemo" src="./git_hub_assets/demonstracao.gif" />
</h1>

# Organização
A lógica do projeto foi organizada com a seguinte formatação. 
<h1 align="center">
  <img alt="Org" title="#Org" src="./git_hub_assets/organizacao.png" />
</h1>

### App
Classe principal do programa. Tem como objetivo iniciar a aplicação chamando o Gui. 
```java
import Gui.UserGui;

public class App {
    public static void main(String[] args) {
        UserGui.show_gui();
    }
}
```
### Gui
Tal classe possui como responsabilidade criar toda a interação gráfica com o usuário. Para tal utilizo o JFrame. Ela também possui interação com a classe lógica do projeto - a classe _Brain_. O método _show_gui_ é responsável por efetivamente mostrar a interface gráfica ao usuário, enquanto _itemStateChanged_ é responsável por ficar ouvindo as mudanças que o usuário causa na Gui. Assim, ao clicar ao selecionar um estado, a barra de aeroportos muda automaticamente para os aeroportos daquele estado. 

### Airports
Uma classe simples para criar a estrutura de um aeroporto. Basicamente, um aeroporto terá uma IATA (String de três letras que representa o aeroporto), um nome, o nome do estado onde ele se localiza, uma latitude, uma longitude (ambas necessárias para calcular a distância de um aeroporto a outro) e as rotas possíveis com outros aeroportos. 
```java
public class Airport {
    String iata, name, state_name;
    double latitude, longitude;
    Vector<String> routes;

    public Airport(
            String iata,
            String name,
            String state_name,
            double latitude,
            double longitude,
            Vector<String> routes
    ) {
        this.iata = iata;
        this.name = name;
        this.state_name = state_name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.routes = routes;
    }
}
```

### Brain
Tal classe possui como responsabilidade criar toda parte lógica do projeto. Ela usa os dados diretamente do banco de dados para realizar tal lógica. Os métodos dessa clase são:
* _get_adjacency_matrix_: tal método irá obter a matriz de ajacência necessária para o algoritmo de Dijkstra.
* _get_index_from_iata_: os aeroportos são guardados em um Map. Aqui, basicamente retornamos qual o indíce do aeroporto dado a sua IATA. Tal método é necessário para implementar a matriz de adjacência. 
* _get_iata_from_complete_name_: método auxiliar para retornar a IATA de um aeroporto dado seu nome completo.
* _get_path_: método que utiliza o algoritmo de Dijkstra para calcular a menor rota. 
* _get_airports_name_: método que retornará o nome completo do aeroporto. 
* _get_states_name_: método que retornará o estado em que o aeroporto está localizado. 

### Dijkstra
Essa classe possui como único objetivo implementar o algoritmo de Dijkstra. Para isso, ela se utiliza de matriz de adjacência. O método utiliza das informações de longitude e latitude do aeroporto. O retorno é uma lista contendo a rota de menor caminho.

### Distance
Essa classe possui como único ojbetivo calcular, dadas as latitudes e longitudes, de dois aeroportos, a distância em linha reta entre eles. Para tal é utlizado o seguinte método:
```java
static public Integer calculate_distance(Double[] loc1, Double[] loc2) {
        double lat1 = loc1[0];
        double lon1 = loc1[1];

        double lat2 = loc2[0];
        double lon2 = loc2[1];

        double rad = Math.PI/180;
        double a = 0.5 - Math.cos((lat2 - lat1) * rad)/2 + Math.cos(lat1 * rad) * Math.cos(lat2 * rad) * (1-Math.cos((lon2 - lon1) * rad))/2;
        return (int) (12742 * Math.asin(Math.sqrt(a)));
    }
```

### GetDataBaseConnection
Classe cuja única responsabilidade é se conectar ao banco de dados MySQL. 
```java
static public Connection get_database_connection() {
        try {
            return DriverManager.getConnection(url, user, password);

        } catch(SQLException e) {
            System.out.println("Exception occurred at getConnection [GetDataBaseConnection] Class\nError: " + e.getMessage());
        }
        return null;
    }
```
Observe que o método retorna uma ```Connection``` caso a conexão seja bem estabelecida. Caso contrário, retornará ```null```, o que ocasionará um erro no programa, e o programa encerrará com uma mensagem de erro ao usuário. 

### GetDataBaseInfos
Classe responsável por coletar os dados sobre os aeroportos no banco de dados SQL. Esse método retorna um Map de aeroportos, com todas as informações dos aeroportos já preenchida - isto é, nome, nome do estado, IATA, latitude, longitude e rotas. 

### SaveInfos
Classe responsável por salvar os dados sobre as pesquisas realizadas. Cada vez que o usuário pesquisa por algo, haverá um salvamento automático diretamente no banco de dados. Como a rota é variável, verifiquei qual era o número máximo de escalas que poderiam ocorrer (que é de 6). Quando houver um valor menor do que 6, então os valores restantes da tabela será preenchida com "XXX". Pode-se ver abaixo uma demonstração do salvamento. 
<h1 align="center">
  <img alt="Save" title="#Save" src="./git_hub_assets/save.png" />
</h1>
