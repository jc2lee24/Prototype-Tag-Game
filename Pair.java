public class Pair<I, P> {
	
	private I UID;
	private P player;

	public Pair(I UID, P player) {
		this.UID = UID;
		this.player = player;
	}
	
	public String toString(){
		return UID + " : " + player;
	}
	
	public I getUID(){
		return UID;
	}
	
	public P getPlayer(){
		return player;
	}

    public void setI(I UID){
        this.UID = UID;
    }

    public void setP(P player){
        this.player = player;
    }
}
