const apiUrl = 'http://localhost:8080/devices';

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
                    <p><strong>Power Consumption:</strong> ${device.basePowerConsumption.toFixed(2)} W</p>
                    <p><strong>Power Level:</strong> ${device.powerLevel}</p>
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

      // Add device to remove device select
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

// Handle Add Device form submission
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
    e.target.reset(); // Clear the form
  } catch (error) {
    console.error('Error adding device:', error);
  }
});

// Handle Remove Device form submission
document.getElementById('remove-device-form').addEventListener('submit', async (e) => {
  e.preventDefault();
  const deviceId = document.getElementById('device-select').value;
  removeDevice(deviceId);
});

// Remove a device
async function removeDevice(deviceId) {
  try {
    await fetch(`${apiUrl}/id/${deviceId}`, { method: 'DELETE' });
    fetchDevices();
  } catch (error) {
    console.error('Error removing device:', error);
  }
}

// Populate a select element with options
async function populateSelectWithOptions(selectElement, options, valueKey, textKey) {
  selectElement.innerHTML = '';
  options.forEach(option => {
    const optionElement = document.createElement('option');
    optionElement.value = option;
    optionElement.textContent = option;
    selectElement.appendChild(optionElement);
  });
}

// Fetch locations and populate select elements
async function fetchLocations() {
  try {
    const response = await fetch(`${apiUrl}/locations`);
    const locations = await response.json();

    const locationRemoveSelect = document.getElementById('location-remove-select');
    const locationAddPersonSelect = document.getElementById('location-add-person-select');
    const locationRemovePersonSelect = document.getElementById('location-remove-person-select');

    await populateSelectWithOptions(locationRemoveSelect, locations, null, option => option);
    await populateSelectWithOptions(locationAddPersonSelect, locations, null, option => option);
    await populateSelectWithOptions(locationRemovePersonSelect, locations, null, option => option);
  } catch (error) {
    console.error('Error fetching locations:', error);
  }
}

// Add a location
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

// Remove a location
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

// Handle Add Location form submission
document.getElementById('add-location-form').addEventListener('submit', async (e) => {
  e.preventDefault();
  const locationName = document.getElementById('location-name').value;
  addLocation(locationName);
  e.target.reset();
});

// Handle Remove Location form submission
document.getElementById('remove-location-form').addEventListener('submit', async (e) => {
  e.preventDefault();
  const locationName = document.getElementById('location-remove-select').value;
  removeLocation(locationName);
});

// Add a person to a location
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

// Remove a person from a location
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

// Handle Add Person form submission
document.getElementById('add-person-form').addEventListener('submit', async (e) => {
  e.preventDefault();
  const locationName = document.getElementById('location-add-person-select').value;
  addPersonToLocation(locationName);
});

// Handle Remove Person form submission
document.getElementById('remove-person-form').addEventListener('submit', async (e) => {
  e.preventDefault();
  const locationName = document.getElementById('location-remove-person-select').value;
  removePersonFromLocation(locationName);
});

// Initial function calls
fetchDevices();
fetchLocations();