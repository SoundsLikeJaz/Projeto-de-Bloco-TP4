import { useState } from 'react';
import './App.css'
import { Card } from './components/card/card';
import { useFoodData } from './hooks/useFoodData';
import { Modal } from './components/modals/form-modal';
import type { FoodData } from './interface/FoodData';

function App() {

  const { data } = useFoodData();
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedFood, setSelectedFood] = useState<FoodData | null>(null);

  function handleOpenModal() {
    setIsModalOpen(prev => !prev);
  }

  function handleEdit(food: FoodData) {
    setSelectedFood(food);
    setIsModalOpen(true);
  }

  return (
    <div className="container">
      <h1>Cardápio</h1>
      <div className="card-grid">
        {data?.map(foodData => 
          <Card 
            code={foodData.id}
            price={foodData.price} 
            title={foodData.title} 
            url={foodData.url}
            onEdit={() => handleEdit(foodData)}
          />
        )}
      </div>
      {isModalOpen && 
        <Modal 
          closeModal={handleOpenModal}
          food={selectedFood}
        />}
      <button onClick={handleOpenModal}>Novo</button>
    </div>
  )
}

export default App
