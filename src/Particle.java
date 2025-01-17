public class Particle {
    public int x,y,sizeX,sizeY;
    public double acceleration,aX,aY,angleToHorizontal,velocity,vX,vY,mass;//all standard units kg,ms^-2 etc
    public Grid g;//prt to current grid square;
    public Particle(int x, int y, double acceleration, double angleToHorizontal, double velocity) {
        this.mass=1;
        this.x = x;
        this.y = y;
        this.sizeX = 20;
        this.sizeY = 20;
        this.acceleration = acceleration;
        this.angleToHorizontal = angleToHorizontal;
        this.velocity=velocity;
        this.setAccelerationComponents();
        this.setVelocityComponents();
    }
    public Particle(int x, int y) {
        this.x = x;
        this.y = y;
        this.sizeX = 20;
        this.sizeY = 20;

    }
    public void setVelocityComponents() {
        this.vX=Math.cos(Math.toRadians(angleToHorizontal))*velocity;
        this.vY=Math.sin(Math.toRadians(angleToHorizontal))*velocity;
    }
    public void setAccelerationComponents() {
        this.aX=Math.cos(Math.toRadians(angleToHorizontal))*acceleration;
        this.aY=Math.sin(Math.toRadians(angleToHorizontal))*acceleration;
    }
    public void updateVelocity() {
        if (velocity>=20){
            acceleration=0;
        }
        velocity+=acceleration;
        setVelocityComponents();
    }
    public void updatePosition() {
    updateVelocity();
    collideBorder();
    this.x+=vX;
    this.y+=vY;
    }
   public boolean collides(Particle particle) {
        double dX = x - particle.x;
        double dY = y - particle.y;
        double distance = Math.sqrt(dX * dX + dY * dY);
       // System.out.println("Checking collision: distance = " + distance + ", threshold = " + sizeX);
        return distance <= sizeX;
    }// sizeX is assumed to be the diameter
    public void collideBorder() {
        // Reverse the horizontal velocity if the particle hits the left or right border
        if (x < 0 || x > 1900) {
            acceleration *= -1;//begins accel in other direction
            velocity*=-1;//update velocity as updating accel doesnt immediately change
            setVelocityComponents();//update velocity components
        }

        // Reverse the vertical velocity if the particle hits the top or bottom border
        if (y < 0 || y >  1030) {
            acceleration *= -1;
            velocity*=-1;
            setVelocityComponents();// Reverse the direction of vertical velocity
        }
    }
    public void handleCollisions() {
        if (g == null) {
            return;
        }
        for (int i = 0; i < g.parts.size(); i++) {
            Particle p = g.parts.get(i);
            if (this != p && collides(p)) {
                // Relative positions (for clarity)
                int dX = p.x - x;
                int dY = p.y - y;

                // Current velocity components
                double v1X = Math.cos(Math.toRadians(angleToHorizontal)) * velocity;
                double v1Y = Math.sin(Math.toRadians(angleToHorizontal)) * velocity;
                double v2X = Math.cos(Math.toRadians(p.angleToHorizontal)) * p.velocity;
                double v2Y = Math.sin(Math.toRadians(p.angleToHorizontal)) * p.velocity;

                // Calculate new velocity components
                double newV1x = ((mass - p.mass) * v1X + 2 * p.mass * v2X) / (mass + p.mass);
                double newV1y = ((mass - p.mass) * v1Y + 2 * p.mass * v2Y) / (mass + p.mass);
                double newV2x = ((p.mass - mass) * v2X + 2 * mass * v1X) / (mass + p.mass);
                double newV2y = ((p.mass - mass) * v2Y + 2 * mass * v1Y) / (mass + p.mass);

                // Update velocities
                velocity = Math.sqrt(newV1x * newV1x + newV1y * newV1y);
                angleToHorizontal = Math.toDegrees(Math.atan2(newV1y, newV1x));
                p.velocity = Math.sqrt(newV2x * newV2x + newV2y * newV2y);
                p.angleToHorizontal = Math.toDegrees(Math.atan2(newV2y, newV2x));

                // Update acceleration based on new velocities
                // Acceleration is related to the change in velocity, assuming a very small timestep for simplicity
                this.acceleration = velocity - Math.sqrt(v1X * v1X + v1Y * v1Y);
                this.setAccelerationComponents();
                p.acceleration = p.velocity - Math.sqrt(v2X * v2X + v2Y * v2Y);
                p.setAccelerationComponents();
                System.out.println("coll");
                // Update velocity components for both particles
                this.setVelocityComponents();
                p.setVelocityComponents();
            }
        }
    }


}

