package ohtuesimerkki;

import org.junit.*;

import ohtuesimerkki.Statistics;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class StatisticsTest {

  Reader readerStub = new Reader() {

    public List<Player> getPlayers() {
      ArrayList<Player> players = new ArrayList<Player>();

      players.add(new Player("Semenko", "EDM", 4, 12));
      players.add(new Player("Lemieux", "PIT", 45, 54));
      players.add(new Player("Kurri", "EDM", 37, 53));
      players.add(new Player("Yzerman", "DET", 42, 56));
      players.add(new Player("Gretzky", "EDM", 35, 89));

      return players;
    }
  };

  Statistics stats;

  @Before
  public void setUp() {
    // luodaan Statistics-olio joka käyttää "stubia"
    stats = new Statistics(readerStub);
  }

  @Test
  public void searchNonExistingPlayer() {
    assertNull(stats.search("Foobar"));
  }

  @Test
  public void searchExistingPlayer() {
    var player = stats.search("Semenko");
    assertEquals(player.toString(), new Player("Semenko", "EDM", 4, 12).toString());
  }

  @Test
  public void testTopScorers() {
    var playerNames = stats.topScorers(2).stream().map(p -> p.getName()).collect(Collectors.toList());
    assertEquals(playerNames, List.of("Gretzky", "Lemieux", "Yzerman"));
  }

  @Test
  public void testTeam() {
    var playerNames = stats.team("EDM").stream().map(p -> p.getName()).collect(Collectors.toList());
    assertEquals(playerNames, List.of("Semenko", "Kurri", "Gretzky"));
  }
}
