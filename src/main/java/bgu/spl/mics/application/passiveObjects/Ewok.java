package bgu.spl.mics.application.passiveObjects;

import java.util.Objects;


/**
 * Passive data-object representing a forest creature summoned when HanSolo and C3PO receive AttackEvents.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You may add fields and methods to this class as you see fit (including public methods).
 */
public class Ewok {
	int serialNumber;
	boolean available;
	boolean inUse=false;

    Object key = new Object();

    public Ewok (int serialNumber)
    {
        this.serialNumber=serialNumber;
        available=true;

    }


    public int getSerialNumber() {
        return serialNumber;
    }



    public  boolean isAvailable() {
        synchronized (key) {

            return available;

        }





    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ewok ewok = (Ewok) o;
        return serialNumber == ewok.serialNumber &&
                available == ewok.available;
    }

    @Override
    public int hashCode() {
        return Objects.hash(serialNumber, available);
    }

    /**
     * Acquires an Ewok
     */
    public  void acquire() {


        synchronized (key) {
            {
                inUse = true;
                this.available = false;
                inUse = false;

            }


        }
    }
        /**
         * release an Ewok
         */
        public  void release () {




            synchronized (key) {
                { inUse = true;
                    this.available = true;
                    inUse = false;


                }

            }



        }
    }
