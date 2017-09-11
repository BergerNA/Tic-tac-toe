package sample;

public enum TypeCell { O, X, Empty;
    int getIntValue(){
        switch (this){
            case O: return 1;
            case X: return -1;
            default: return 0;
        }

    }
}
