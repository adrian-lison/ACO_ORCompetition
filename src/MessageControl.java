
public class MessageControl {
	
boolean generalS=true;
boolean errorS=true;
boolean roundresultS=false;
boolean finalresultS=true;
boolean heuristicS=false;
boolean pheromoneS=true;
boolean solutionS=true;
boolean logicS=true;
boolean finalresultroundS=false;
	
public void general(String s){if(generalS) System.out.println(s);}
public void error(String s){if(errorS) System.out.println(s);}

public void newline(){System.out.println("\n");}
public void paragraph(){System.out.println("\n\n");}

public void roundresult(String s){if(roundresultS) System.out.println(s);}
public void finalresultround(String s){if(finalresultroundS) System.out.println(s);}
public void finalresult(String s){if(finalresultS) System.out.println(s);}

public void heuristic(String s){if(heuristicS) System.out.println(s);}
public void pheromone(String s){if(pheromoneS) System.out.println(s);}
public void solution(String s){if(solutionS) System.out.println(s);}
public void logic(String s){if(logicS) System.out.println(s);}
}
