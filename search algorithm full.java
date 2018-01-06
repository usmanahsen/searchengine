public static void SearchWord(Hashtable<String, ArrayList> v, String word) {
        if (v.containsKey(word)) {
            int n = v.get(word).size();
            for (int x = 0; x < n && x < 10; x++) {
                System.out.println("KeyWord Found on page " + v.get(word).get(x));
            }
        } else {
            System.out.println("KeyWord not Found ");
        }
    }