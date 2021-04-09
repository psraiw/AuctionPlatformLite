import java.util.*;
import java.io.*;

class Userbids{
    int userId, itemId, statusBid;//maxbid = 1, notmax = 0, failed = -1
    double userBidAmount;
    Boolean isMax;
    public Userbids(int userId, int itemId, int statusBid, double userBidAmount, Boolean isMax) {
        this.userId = userId;
        this.itemId = itemId;
        this.statusBid = statusBid;
        this.userBidAmount = userBidAmount;
        this.isMax = isMax;
    }

    public addBid(){
        //user bid enter
        //check database timestamp
        //if bid > max : item-> maxbid(update)
        //statusBid - Accepted
    }

    public updateAllusers
}
