import java.util.HashSet;
import java.util.Set;

public class HasDuplicatedChars {
  public static boolean hasDuplicatedChars(String text) {
    Set<Character> chars = new HashSet<>();
    for (char ch: text.toCharArray()) {
      if (chars.contains(ch)) {
        return true;
      }
      chars.add(ch);
    }
    return false;
  }

  public static void main(String[] args) {
    String text1 = "Hello";
    String text2 = "ABC";

    System.out.println(hasDuplicatedChars(text1)); // true
    System.out.println(hasDuplicatedChars(text2)); // false
  }
}
