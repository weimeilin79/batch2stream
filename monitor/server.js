const express = require('express');
const WebSocket = require('ws');
const http = require('http');

const cassandra = require('cassandra-driver');

// Connect to Cassandra
const client = new cassandra.Client({
  contactPoints: ['cassandra'],
  localDataCenter: 'datacenter1',
  keyspace: 'demo'
});

client.connect(err => {
  if (err) return console.error(err);
  console.log('Connected to Cassandra');
});

// Function to fetch data from Cassandra
async function fetchData() {
  const query = 'SELECT callsign, longitude, latitude, on_ground, squawk FROM latest_flight_data';
  try {
    const result = await client.execute(query);
    return result.rows;
  } catch (error) {
    console.error('Failed to fetch data from Cassandra:', error);
    return [];
  }
}


// Initialize express app and create a server
const app = express();
const server = http.createServer(app);

// Serve static files from a 'public' folder
app.use(express.static('public'));

// WebSocket server
const wss = new WebSocket.Server({ server });

wss.onmessage = function(event) {
    map.eachLayer(function(layer) {
        if (layer instanceof L.Marker) {
            map.removeLayer(layer);  // Remove existing markers before adding new ones
        }
    });

    const flights = JSON.parse(event.data);
    flights.forEach(flight => {
        const marker = L.marker([flight.latitude, flight.longitude]).addTo(map);
        marker.bindPopup(`Callsign: ${flight.callsign}`).openPopup();
    });
};

// Function to broadcast data to all connected clients
function broadcast(data) {
  wss.clients.forEach(client => {
    if (client.readyState === WebSocket.OPEN) {
      client.send(data);
    }
  });
}

// Set interval to fetch data and broadcast
setInterval(() => {
  fetchData().then(data => {
    broadcast(JSON.stringify(data));
  });
}, 10000); // Update every second

// Start the server
const PORT = process.env.PORT || 3000;
server.listen(PORT, () => console.log(`Server is running on http://localhost:${PORT}`));
