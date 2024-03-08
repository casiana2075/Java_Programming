public class Vehicle {
        private String name;
        private Depot depot;
        
        public Vehicle() { }
        public Vehicle(String name) {
            this.name = name;
        }
    
        //getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Depot getDepot() {
            return depot;
        }

        public void setDepot(Depot depot) {
            if (this.depot == null) {
                this.depot = depot;
            } else {
                System.out.printf("The vehicle '%s' is already assigned to depot '%s'.\n", name, this.depot);
            }
        }

        //toString
        @Override
        public String toString(){ 
            return "Vehicle:" + name;
        }
    
        @Override
        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Vehicle)) {
                return false;
            }
            Vehicle other = (Vehicle) obj;
            return name.equals(other.name);
        }
   }
   