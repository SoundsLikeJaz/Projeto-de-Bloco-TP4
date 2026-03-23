import { useEffect, useState } from "react";
import { useFoodDataMutate } from "../../hooks/useFoodDataMutate";
import type { FoodData } from "../../interface/FoodData";
import "./modal.css";

interface InputProps {
    label: string;
    value: string | number;
    updateValue(value: any): void;
};

interface ModalProps {
    closeModal(): void;
    food?: FoodData | null;
}

const Input = ({label, value, updateValue}: InputProps) => {
    return (
        <>
            <label>{label}</label>
            <input value={value} onChange={e => updateValue(e.target.value)} />
        </>
    )
}

export function Modal({ closeModal, food }: ModalProps) {
    const [title, setTitle] = useState("");
    const [imageUrl, setImageUrl] = useState("");
    const [price, setPrice] = useState(0);
    const [errorMessage, setErrorMessage] = useState("");

    useEffect(() => {
        if (food) {
            setTitle(food.title);
            setImageUrl(food.url);
            setPrice(food.price);
        }
    }, [food]);

    const { createMutate, updateMutate } = useFoodDataMutate();

    const submit = () => {

    const foodData: FoodData = {
        title,
        url: imageUrl,
        price: Number(price)
    }

    if (!title || !imageUrl || !price) {
        setErrorMessage("Preencha todos os campos");
        return;
    }

    if (food?.id) {
        updateMutate.mutate({ ...foodData, id: food.id });
    } else {
        createMutate.mutate(foodData);
    }
    console.log(`Food data submitted: ${JSON.stringify(foodData)}`);
}

    useEffect(() => {
        if (createMutate.isSuccess || updateMutate.isSuccess) {
            closeModal();
            setTitle("");
            setImageUrl("");
            setPrice(0);
        }
    }, [createMutate.isSuccess, updateMutate.isSuccess])
    
    return(
        <div className="modal-overlay">
            <div className="modal-body">
                <div className="modal-top">
                    <h2>
                        {food ? "Editar item do cardápio" : "Cadastrar novo item"}
                    </h2>
                    <button onClick={closeModal} className="btn-close">X</button>
                </div>
                <form className="input-container">
                    <Input 
                        label="Título" 
                        value={title}
                        updateValue={setTitle} 
                    />
                    <Input 
                        label="URL da imagem" 
                        value={imageUrl}
                        updateValue={setImageUrl} 
                    />
                    <Input 
                        label="Preço" 
                        value={price} 
                        updateValue={setPrice} 
                    />
                </form>
                {errorMessage && <p className="error-message">{errorMessage}</p>}
                <button onClick={submit} className="btn-secondary">
                    {createMutate.isPending || updateMutate.isPending ? "Cadastrando..." : "Cadastrar"}
                </button>
            </div>
        </div>
    )
}