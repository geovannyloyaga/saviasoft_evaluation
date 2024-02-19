import { Routes, Route } from "react-router-dom";
import Person from "./components/person";

function App() {
  return (
    <div>
      <Routes>
        <Route path="/" element={<Person />} />
      </Routes>
    </div>
  );
}

export default App;
