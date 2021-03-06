package buildcraft.core;

import buildcraft.api.APIProxy;
import buildcraft.api.IAreaProvider;
import buildcraft.api.IBox;
import buildcraft.api.LaserKind;
import buildcraft.api.Position;
import buildcraft.api.TileNetworkData;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.World;

public class Box implements IBox
{
    @TileNetworkData
    public int xMin;
    @TileNetworkData
    public int yMin;
    @TileNetworkData
    public int zMin;
    @TileNetworkData
    public int xMax;
    @TileNetworkData
    public int yMax;
    @TileNetworkData
    public int zMax;
    private EntityBlock[] lasers;

    public void initialize(int var1, int var2, int var3, int var4, int var5, int var6)
    {
        this.xMin = var1;
        this.yMin = var2;
        this.zMin = var3;
        this.xMax = var4;
        this.yMax = var5;
        this.zMax = var6;
    }

    public void initialize(IAreaProvider var1)
    {
        this.xMin = var1.xMin();
        this.yMin = var1.yMin();
        this.zMin = var1.zMin();
        this.xMax = var1.xMax();
        this.yMax = var1.yMax();
        this.zMax = var1.zMax();
    }

    public void initialize(NBTTagCompound var1)
    {
        this.xMin = var1.getInt("xMin");
        this.yMin = var1.getInt("yMin");
        this.zMin = var1.getInt("zMin");
        this.xMax = var1.getInt("xMax");
        this.yMax = var1.getInt("yMax");
        this.zMax = var1.getInt("zMax");
    }

    public Box()
    {
        this.reset();
    }

    public boolean isInitialized()
    {
        return this.xMin != Integer.MAX_VALUE;
    }

    public void reset()
    {
        this.xMin = Integer.MAX_VALUE;
        this.yMin = Integer.MAX_VALUE;
        this.zMin = Integer.MAX_VALUE;
        this.xMax = Integer.MAX_VALUE;
        this.yMax = Integer.MAX_VALUE;
        this.zMax = Integer.MAX_VALUE;
    }

    public static int packetSize()
    {
        return 6;
    }

    public Position p1()
    {
        return new Position((double)this.xMin, (double)this.yMin, (double)this.zMin);
    }

    public Position p2()
    {
        return new Position((double)this.xMax, (double)this.yMax, (double)this.zMax);
    }

    public void createLasers(World var1, LaserKind var2)
    {
        if (this.lasers == null)
        {
            this.lasers = Utils.createLaserBox(var1, (double)this.xMin, (double)this.yMin, (double)this.zMin, (double)this.xMax, (double)this.yMax, (double)this.zMax, var2);
        }
    }

    public void deleteLasers()
    {
        if (this.lasers != null)
        {
            EntityBlock[] var1 = this.lasers;
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3)
            {
                EntityBlock var4 = var1[var3];
                APIProxy.removeEntity(var4);
            }

            this.lasers = null;
        }
    }

    public void writeToNBT(NBTTagCompound var1)
    {
        var1.setInt("xMin", this.xMin);
        var1.setInt("yMin", this.yMin);
        var1.setInt("zMin", this.zMin);
        var1.setInt("xMax", this.xMax);
        var1.setInt("yMax", this.yMax);
        var1.setInt("zMax", this.zMax);
    }

    public int sizeX()
    {
        return this.xMax - this.xMin + 1;
    }

    public int sizeY()
    {
        return this.yMax - this.yMin + 1;
    }

    public int sizeZ()
    {
        return this.zMax - this.zMin + 1;
    }

    public String toString()
    {
        return "{" + this.xMin + ", " + this.xMax + "}, {" + this.yMin + ", " + this.yMax + "}, {" + this.zMin + ", " + this.zMax + "}";
    }
}
