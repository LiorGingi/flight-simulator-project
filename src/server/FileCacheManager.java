package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

public class FileCacheManager<Problem, Solution> implements CacheManager<Problem, Solution> {

	private static final String SOLVED_PROBLEMS_FILE_NAME = "SolvedProblems.txt";
	private HashSet<String> hs;// contians the names of problems that the server has solution for.

	public FileCacheManager() {
		File solvedProblemsFile = new File(SOLVED_PROBLEMS_FILE_NAME);
		hs = new HashSet<String>();
		try {
			boolean newFileCreated = solvedProblemsFile.createNewFile();
			if (!newFileCreated) {// if the file isn't just created
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new FileReader(SOLVED_PROBLEMS_FILE_NAME));
					try {
						String p;
						while ((p = reader.readLine()) != null) {
							hs.add(p);// loads problem name (without '.txt')
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} finally {
					reader.close();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public boolean isExist(Problem p) {
		return hs.contains(p.toString());
	}

	@Override
	public Solution get(Problem p) {
		if (!this.isExist(p))
			return null;
		
		ObjectInputStream objectInFromFile = null;
		try {
			objectInFromFile = new ObjectInputStream(new FileInputStream(p.toString() + ".txt"));
			return (Solution) objectInFromFile.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (objectInFromFile != null) {
					objectInFromFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void save(Problem p, Solution s) {
		ObjectOutputStream objectOutToFile = null;
		try {
			objectOutToFile = new ObjectOutputStream(new FileOutputStream(p.toString() + ".txt"));
			objectOutToFile.writeObject(s);
			if (!this.hs.contains(p.toString())) {
				this.hs.add(p.toString());
				this.saveSolvedProblemsToFile();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(objectOutToFile !=null) {
					objectOutToFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void saveSolvedProblemsToFile() {
		BufferedWriter outToFile = null;
		try {
			outToFile = new BufferedWriter(new FileWriter(SOLVED_PROBLEMS_FILE_NAME));
			for (String str : this.hs) {
				try {
					outToFile.write(str + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if(outToFile !=null) {
					outToFile.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
