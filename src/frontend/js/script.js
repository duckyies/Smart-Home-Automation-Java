const apiUrl = 'http://localhost:8080/devices';
let powerConsumptionChart, deviceTypeChart, deviceLocationChart; // Declare chart variables globally


// --- Page Switching Logic ---
function showPage(pageId) {
  // Hide all pages
  document.querySelectorAll('.page').forEach(page => {
    page.style.display = 'none';
  });

  // Show the selected page
  document.getElementById(pageId).style.display = 'block';

  if (pageId === 'page2') {
    updateCharts();
  }
}

// --- Chart Initialization (Called once on initial load) ---
function initializeCharts() {
  const powerCtx = document.getElementById('powerConsumptionChart').getContext('2d');
  powerConsumptionChart = new Chart(powerCtx, {
    type: 'bar', // Or 'line', 'pie', etc.
    data: {
      labels: [], // Device names or other labels
      datasets: [{
        label: 'Power Consumption (W)',
        data: [], // Power consumption data
        backgroundColor: 'rgba(255, 99, 132, 0.5)',
        borderColor: 'rgba(255, 99, 132, 1)',
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        y: {
          beginAtZero: true
        }
      }
    }
  });


  const typeCtx = document.getElementById('deviceTypeChart').getContext('2d');
  deviceTypeChart = new Chart(typeCtx, {
    type: 'pie', // Use a pie chart for device types
    data: {
      labels: [], // Device type names
      datasets: [{
        label: 'Number of Devices',
        data: [], // Count of devices per type
        backgroundColor: [
          'rgba(255, 99, 132, 0.5)',
          'rgba(54, 162, 235, 0.5)',
          'rgba(255, 206, 86, 0.5)',
          'rgba(75, 192, 192, 0.5)',
          'rgba(153, 102, 255, 0.5)',
          'rgba(255, 159, 64, 0.5)'
          // Add more colors as needed
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)'
          // Add more colors as needed
        ],
        borderWidth: 1
      }]
    },
  });
  const locationCtx = document.getElementById('deviceLocationChart').getContext('2d');
  deviceLocationChart = new Chart(locationCtx, {
    type: 'doughnut',
    data: {
      labels: [], // Device location names
      datasets: [{
        label: 'Number of Devices',
        data: [],
        backgroundColor: [
          'rgba(255, 99, 132, 0.5)',
          'rgba(54, 162, 235, 0.5)',
          'rgba(255, 206, 86, 0.5)',
          // Add more colors as needed
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          // Add more colors as needed
        ],
        borderWidth: 1
      }]
    },

  });
}

// --- Chart Update Function (Called when switching to page2) ---
async function updateCharts() {
  try {
    const devicesResponse = await fetch(apiUrl);
    const devices = await devicesResponse.json();

    // Power Consumption Chart Update
    const powerData = devices.map(device => device.basePowerConsumption * (device.powerLevel > 0 ? device.powerLevel : 1));
    const powerLabels = devices.map(device => device.deviceName);
    powerConsumptionChart.data.labels = powerLabels;
    powerConsumptionChart.data.datasets[0].data = powerData;
    powerConsumptionChart.update();

    // Device Type Chart Update
    const typeCounts = {};
    devices.forEach(device => {
      typeCounts[device.deviceType] = (typeCounts[device.deviceType] || 0) + 1;
    });
    deviceTypeChart.data.labels = Object.keys(typeCounts);
    deviceTypeChart.data.datasets[0].data = Object.values(typeCounts);
    deviceTypeChart.update();


    // Device Location Chart Update
    const locationCounts = {};
    devices.forEach(device => {
      if (locationCounts[device.location]) {
        locationCounts[device.location]++;
      } else {
        locationCounts[device.location] = 1;
      }
    });

    const locationLabels = Object.keys(locationCounts);
    const locationData = Object.values(locationCounts);


    deviceLocationChart.data.labels = locationLabels;
    deviceLocationChart.data.datasets[0].data = locationData;


    const backgroundColors = [];
    const borderColors = [];
    for (let i = 0; i < locationLabels.length; i++) {
      const color = `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, ${Math.floor(Math.random() * 256)}, 0.5)`;
      const borderColor = color.replace('0.5', '1'); // Make border solid
      backgroundColors.push(color);
      borderColors.push(borderColor);
    }
    deviceLocationChart.data.datasets[0].backgroundColor = backgroundColors;
    deviceLocationChart.data.datasets[0].borderColor = borderColors;
    deviceLocationChart.update();

  } catch (error) {
    console.error('Error updating charts:', error);
  }
}

// --- Existing fetchDevices, toggleDevice, etc. functions ---

async function fetchDevices() {
  try {
    const response = await fetch(apiUrl);
    const devices = await response.json();

    const poweredOnContainer = document.getElementById('powered-on-devices');
    const poweredOffContainer = document.getElementById('powered-off-devices');
    const removeDeviceSelect = document.getElementById('device-select');

    poweredOnContainer.innerHTML = '';
    poweredOffContainer.innerHTML = '';
    removeDeviceSelect.innerHTML = '';

    devices.forEach(device => {
      const deviceElement = document.createElement('div');
      deviceElement.className = 'device';

      deviceElement.innerHTML = `
            <div class="device-info">
                <p><strong>Name:</strong> ${device.deviceName}</p>
                <p><strong>Type:</strong> ${device.deviceType}</p>
                <p><strong>Location:</strong> ${device.location}</p>
                <p><strong>Power Consumption:</strong> ${device.basePowerConsumption.toFixed(3)} W</p>
                <p><strong>Power Level:</strong> ${device.powerLevel}</p>
                <p><strong>Battery Level:</strong> ${device.batteryLevel}</p>
            </div>
            <div class="device-controls">
                <button class="${device.isTurnedOn ? 'off' : 'on'}" onclick="toggleDevice(${device.deviceID}, ${device.isTurnedOn})">
                    Turn ${device.isTurnedOn ? 'Off' : 'On'}
                </button>
            </div>
        `;

      if (device.isTurnedOn) {
        poweredOnContainer.appendChild(deviceElement);
      } else {
        poweredOffContainer.appendChild(deviceElement);
      }

      const option = document.createElement('option');
      option.value = device.deviceID;
      option.textContent = `${device.deviceName} (${device.location})`;
      removeDeviceSelect.appendChild(option);
    });


  } catch (error) {
    console.error('Error fetching devices:', error);
  }
}

async function toggleDevice(id, isPoweredOn) {
  try {
    const endpoint = `${apiUrl}/id/${id}/${isPoweredOn ? 'off' : 'on'}`;
    await fetch(endpoint, { method: 'PUT' });
    fetchDevices();
  } catch (error) {
    console.error('Error toggling device:', error);
  }
}

document.getElementById('add-device-form').addEventListener('submit', async (e) => {
  e.preventDefault();

  const deviceName = document.getElementById('device-name').value;
  const deviceType = document.getElementById('device-type').value;
  const deviceGroup = document.getElementById('device-group').value;
  const location = document.getElementById('device-location').value;
  const basePowerConsumption = document.getElementById('device-power-consumption').value;
  const isTurnedOn = true;
  const powerLevel = document.getElementById('device-power-level').value;
  const maxBatteryCapacity = document.getElementById('device-max-battery-capacity').value;
  const batteryLevel = 100;

  const newDevice = {
    deviceName,
    deviceType,
    deviceGroup,
    location,
    isTurnedOn,
    batteryLevel,
    basePowerConsumption,
    maxBatteryCapacity,
    powerLevel
  };

  try {
    await fetch(apiUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(newDevice)
    });

    fetchDevices();
    e.target.reset();

  } catch (error) {
    console.error('Error adding device:', error);
  }
});

document.getElementById('remove-device-form').addEventListener('submit', async (e) => {
  e.preventDefault();
  const deviceId = document.getElementById('device-select').value;
  removeDevice(deviceId);
});

async function removeDevice(deviceId) {
  try {
    await fetch(`${apiUrl}/id/${deviceId}`, { method: 'DELETE' });
        fetchDevices();
    } catch (error) {
        console.error('Error removing device:', error);
    }
}

async function populateSelectWithOptions(selectElement, options, valueKey, textKey) {
    selectElement.innerHTML = '';
    options.forEach(option => {
        const optionElement = document.createElement('option');
        optionElement.value = option;
        optionElement.textContent = option;
        selectElement.appendChild(optionElement);
    });
}

async function fetchLocations() {
    try {
        const response = await fetch(`${apiUrl}/locations`);
    const locations = await response.json();

    const locationAddPersonSelect = document.getElementById('location-add-person-select');
    const locationRemovePersonSelect = document.getElementById('location-remove-person-select');

    await populateSelectWithOptions(locationAddPersonSelect, locations, null, option => option);
    await populateSelectWithOptions(locationRemovePersonSelect, locations, null, option => option);

  } catch (error) {
    console.error('Error fetching locations:', error);
  }
}

async function addLocation(locationName) {
  try {
    const response = await fetch(`${apiUrl}/locations`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name: locationName })
    });
    if (response.ok) {
      await fetchLocations();
      alert('Location added successfully!');
    } else {
      alert('Failed to add location.');
    }
  } catch (error) {
    console.error('Error adding location:', error);
    alert('Error adding location.');
  }
}

async function removeLocation(locationName) {
  try {
    const response = await fetch(`${apiUrl}/locations/${locationName}`, { method: 'DELETE' });
        if (response.ok) {
            fetchLocations();
            alert('Location removed successfully!');
        } else {
            alert('Failed to remove location.');
        }
    } catch (error) {
        console.error('Error removing location:', error);
        alert('Error removing location.');
    }
}

document.getElementById('remove-location-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const locationName = document.getElementById('location-remove-select').value;
    removeLocation(locationName);
});

async function addPersonToLocation(locationName) {
    try {
        const response = await fetch(`${apiUrl}/location/${locationName}/add_person`, { method: 'PUT' });
    if (response.ok) {
      alert('Person added to location successfully!');
    } else {
      alert('Failed to add person to location.');
    }
  } catch (error) {
    console.error('Error adding person to location:', error);
    alert('Error adding person to location.');
  }
}

async function removePersonFromLocation(locationName) {
  try {
    const response = await fetch(`${apiUrl}/location/${locationName}/remove_person`, { method: 'PUT' });
        if (response.ok) {
            alert('Person removed from location successfully!');
        } else {
            alert('Failed to remove person from location.');
        }
    } catch (error) {
        console.error('Error removing person from location:', error);
        alert('Error removing person from location.');
    }
}

document.getElementById('add-person-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const locationName = document.getElementById('location-add-person-select').value;
    addPersonToLocation(locationName);
});

document.getElementById('remove-person-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    const locationName = document.getElementById('location-remove-person-select').value;
    removePersonFromLocation(locationName);
});

async function updateLogBoxes() {
    const logUrls = [
        { id: 'info-logs', url: '/log/info' },
        { id: 'warning-logs', url: '/log/warning' },
        { id: 'error-logs', url: '/log/severe' },
        { id: 'debug-logs', url: '/log/battery' },
        { id: 'verbose-logs', url: '/log/power_consumption' },
    ];

    for (const log of logUrls) {
        try {
            const response = await fetch(apiUrl + log.url);
            const logText = (await response.text()).replaceAll('"', '').replaceAll("[", "").replaceAll("]", "");
            const logBox = document.getElementById(log.id);
            if (logText != "") logBox.textContent += logText + '\n';

            logBox.parentNode.scrollTop = logBox.parentNode.scrollHeight;

        } catch (error) {
            console.error(`Error fetching ${log.id}:`, error);
        }
    }
}

// --- Debug Section Functions ---

async function fetchAndDisplayDeviceDetails(deviceId) {
    try {
        const response = await fetch(`${apiUrl}/id/${deviceId}`);
        const device = await response.json();

        const deviceDetailsContainer = document.getElementById('device-details');
        deviceDetailsContainer.innerHTML = `
    <h3>Device Details: ${device.deviceName}</h3>
    <p><strong>ID:</strong> ${device.deviceID}</p>
    <p><strong>Type:</strong> ${device.deviceType}</p>
    <p><strong>Group:</strong> ${device.deviceGroup}</p>
    <p><strong>Location:</strong> ${device.location}</p>
    <p><strong>Status:</strong> ${device.isTurnedOn ? 'On' : 'Off'}</p>
    <p><strong>Power Consumption:</strong> ${device.basePowerConsumption} W</p>
    <p><strong>Power Level:</strong> ${device.powerLevel}</p>
    <p><strong>Battery Level:</strong> ${device.batteryLevel}%</p>
    <p><strong>Max Battery Capacity:</strong> ${device.maxBatteryCapacity} mAH</p>
    <p><strong>On Battery:</strong> ${device.onBattery}</p>
    <p><strong>Interaction:</strong> ${device.interaction}</p>
    <p><strong>Current Battery Capacity:</strong> ${device.currentBatteryCapacity} mAH</p>`;


    const poweredOnDevicesList = document.getElementById('powered-on-devices');
    if (poweredOnDevicesList) {
        poweredOnDevicesList.querySelectorAll('.device').forEach(deviceElement => {
            deviceElement.addEventListener('click', () => {
                const deviceId = deviceElement.querySelector('button').getAttribute('onclick').match(/toggleDevice\((\d+),/)[1];
                fetchAndDisplayDeviceDetails(deviceId);
                showPage('page3'); // Switch to the debug page
            });
        });
    }

    const poweredOffDevicesList = document.getElementById('powered-off-devices');
    if (poweredOffDevicesList) {
        poweredOffDevicesList.querySelectorAll('.device').forEach(deviceElement => {
            deviceElement.addEventListener('click', () => {
                const deviceId = deviceElement.querySelector('button').getAttribute('onclick').match(/toggleDevice\((\d+),/)[1];
                fetchAndDisplayDeviceDetails(deviceId);
                showPage('page3');
            });
        });
    }

    } catch (error) {
        console.error('Error fetching device details:', error);
    }
}
async function fetchAndDisplayDataStructures() {
    try {
        // Fetch LinkedLists
        const linkedListsResponse = await fetch(`${apiUrl}/debug/linkedlists`);
        if (linkedListsResponse.ok) {
            const linkedLists = await linkedListsResponse.json();
            const linkedListsContainer = document.getElementById('linked-lists-info');
            linkedListsContainer.innerHTML = '<h4>Linked Lists</h4>';
            const ul = document.createElement('ul');
            for (const listName in linkedLists) {
                const li = document.createElement('li');
                li.textContent = `${listName}: ${JSON.stringify(linkedLists[listName])}`;
                ul.appendChild(li);
            }
            linkedListsContainer.appendChild(ul);
        }

        // Fetch PriorityQueues
        const priorityQueuesResponse = await fetch(`${apiUrl}/debug/priorityqueues`);
        if (priorityQueuesResponse.ok) {
            const priorityQueues = await priorityQueuesResponse.json();
            const priorityQueuesContainer = document.getElementById('priority-queues-info');
            priorityQueuesContainer.innerHTML = '<h4>Priority Queues</h4>';

              const ul = document.createElement('ul');
            for (const queueName in priorityQueues) {
                const li = document.createElement('li');
                li.textContent = `${queueName}: ${JSON.stringify(priorityQueues[queueName])}`;
                ul.appendChild(li);
            }
            priorityQueuesContainer.appendChild(ul);
        }


    } catch (error) {
        console.error('Error fetching data structures:', error);
    }
}


// --- Initial Function Calls ---
initializeCharts();
fetchDevices();
fetchLocations();
updateLogBoxes();
setInterval(updateLogBoxes, 5000);
setInterval(fetchDevices, 1000);
setInterval(fetchLocations, 1100);
setInterval(fetchAndDisplayDataStructures, 5000);