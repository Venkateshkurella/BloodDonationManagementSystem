# Deployment Guide - Blood Donation Management System

This guide outlines three methods to access and share the Blood Donation Management System on mobile phones and desktops.

---

## Method 1: Local Wi-Fi Network Sharing (LAN)
If you want to open and test the application on your mobile phone while it runs on your laptop (without uploading any files to the internet), use your local Wi-Fi connection.

### Steps:
1. Ensure both your **mobile phone** and **laptop** are connected to the **same Wi-Fi router**.
2. Find your laptop's Local IP Address:
   - Open a Command Prompt (`cmd`) on your laptop.
   - Type `ipconfig` and press Enter.
   - Look for the **IPv4 Address** under your active wireless connection (e.g., `192.168.1.15`).
3. Launch your local servers (e.g., via `start-cluster.bat` or single run).
4. On your mobile phone, open the browser and navigate to:
   - `http://<YOUR_IP_ADDRESS>:8080` (e.g., `http://192.168.1.15:8080`)
   - Or, if you run the Nginx Load Balancer, go directly to: `http://<YOUR_IP_ADDRESS>`

---

## Method 2: Global Internet Tunneling (Ngrok) - *Fastest Global Option*
If you want to share a public link with anyone on the internet to test on their mobile or desktop, you can create a secure tunnel directly to your local machine using **Ngrok**.

### Steps:
1. Download **[Ngrok for Windows](https://ngrok.com/download)** and extract it.
2. Sign up for a free account to get your authentication token.
3. Open a Command Prompt in the Ngrok directory and run:
   ```cmd
   ngrok config add-authtoken YOUR_AUTHTOKEN
   ```
4. Expose your port (use `8080` for Instance 1, or `80` if running the Nginx Load Balancer):
   ```cmd
   ngrok http 8080
   ```
5. Ngrok will display a public forwarding URL:
   - e.g., `https://a1b2-34-56.ngrok-free.app`
6. Open that HTTPS URL on **any mobile device or desktop anywhere in the world** to access your application live!

---

## Method 3: Permanent Cloud Deployment (Railway.app)
To host the application permanently on the cloud (so it stays running even when your laptop is closed), you can deploy it to **Railway.app** or **Render.com** using the provided `Dockerfile`.

### Steps:
1. **GitHub Upload**:
   - Initialize git in your project root, commit all files (including the `Dockerfile`), and push them to a private or public repository on **GitHub**.
2. **Setup Cloud Database (PaaS)**:
   - Log in to **[Railway.app](https://railway.app)**.
   - Click **New Project** -> **Provision MySQL**.
   - Under the MySQL service, click **Variables** and copy the Connection URL, Username, and Password.
3. **Deploy the Web Service**:
   - Click **New** -> **GitHub Repo** -> select your uploaded repository.
   - Under the service **Settings**, Railway will automatically detect the `Dockerfile` and select it as the build system.
4. **Configure Environment Variables**:
   - Under the Web Service **Variables** tab, add the following variables matching the copied MySQL credentials:
     - `SPRING_DATASOURCE_URL` = `jdbc:mysql://<host>:<port>/<database>` (or the Railway private URL)
     - `SPRING_DATASOURCE_USERNAME` = `root` (or the Railway user)
     - `SPRING_DATASOURCE_PASSWORD` = `<database_password>`
5. **Start and Access**:
   - Railway will compile, package, containerize, and start your Spring Boot application automatically.
   - Go to the **Settings** tab -> click **Generate Domain** to get a public `railway.app` URL. You can now access your real-time application globally from any mobile or desktop device!
