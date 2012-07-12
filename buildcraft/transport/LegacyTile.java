package buildcraft.transport;

import net.minecraft.server.Block;
import net.minecraft.server.BuildCraftTransport;
import net.minecraft.server.TileEntity;

public class LegacyTile extends TileEntity
{
    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    public void q_()
    {
        int var1 = this.world.getData(this.x, this.y, this.z);
        int var2 = ((LegacyBlock)Block.byId[this.world.getTypeId(this.x, this.y, this.z)]).newPipeId;
        // Mae start
        //BlockGenericPipe.createPipe(this.world, this.x, this.y, this.z, var2);
        Pipe pipe = BlockGenericPipe.createPipe(this.world, this.x, this.y, this.z, var2);
        if (pipe == null) {
            System.err.println("[BuildCraft] Pipe failed to load from legacy block at "+this.x+","+this.y+","+this.z);
        }
        // Mae end
        this.world.setTypeIdAndData(this.x, this.y, this.z, BuildCraftTransport.genericPipeBlock.id, var1);
    }
}
