import React from 'react';
import './App.css';
import PicoPlacaForm from './components/PicoPlacaForm';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Pico y Placa Checker</h1>
      </header>
      <main>
        <PicoPlacaForm />
      </main>
    </div>
  );
}

export default App;