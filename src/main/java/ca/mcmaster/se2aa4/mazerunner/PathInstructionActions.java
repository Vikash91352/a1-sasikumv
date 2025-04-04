package ca.mcmaster.se2aa4.mazerunner;

public class PathInstructionActions {

    public String convertToFactorizedPath(String unconvertedPath) {
        
        StringBuffer factorizedPathForm = new StringBuffer();

        char currentChar = ' ';
        int currentCharCount = 0;
        for (int i = 0; i < unconvertedPath.length(); i++) {
 
            if (i == 0) {
                currentChar = unconvertedPath.charAt(i);
                currentCharCount = 1;

            }else if (currentChar == unconvertedPath.charAt(i)) {
                currentCharCount += 1;

            }else if (!(currentChar == unconvertedPath.charAt(i))) {
                factorizedPathForm.append(Integer.toString(currentCharCount));
                factorizedPathForm.append(Character.toString(currentChar));

                currentChar = unconvertedPath.charAt(i);
                currentCharCount = 1;

            }
            if (i == (unconvertedPath.length() - 1)) {
                factorizedPathForm.append(Integer.toString(currentCharCount));
                factorizedPathForm.append(Character.toString(currentChar));

            }

        }

        return factorizedPathForm.toString();

    }
    
    public void printPath(String pathInstruction){

        System.out.println("The Factorized Path is " + pathInstruction);

    }

    
}