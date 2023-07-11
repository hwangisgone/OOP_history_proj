package database;

import java.util.List;
import java.util.function.Supplier;

import entity.HistoricalEventWar;
public class EventDatabase implements IDatabase<HistoricalEventWar>{

	@Override
	public void store(List<HistoricalEventWar> listObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<HistoricalEventWar> load() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HistoricalEventWar> loadOr(Supplier<List<HistoricalEventWar>> getList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}
    
}
