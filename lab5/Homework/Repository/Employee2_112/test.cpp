#include <iostream>
using namespace std;

int main() {
    int f1 = 1; // f(1)
    int f2 = 2; // f(2)
    int fn;

    // Iterăm pentru a calcula f(3) până la f(20)
    for (int n = 3; n <= 20; ++n) {
        if (n % 2 == 0) {
            fn = (2 * f2 + 3 * f1) % 1000;
        } else {
            fn = (f2 + 2 * f1) % 1000;
        }
        // Actualizăm valorile pentru următoarea iterație
        f1 = f2;
        f2 = fn;
    }

    cout << fn << endl; // Afisam ultimele trei cifre ale lui f(20)
    
    return 0;
}
