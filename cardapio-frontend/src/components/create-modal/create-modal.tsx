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
}

const Input = ({label, value, updateValue}: InputProps) => {
    return (
        <>
            <label>{label}</label>
            <input value={value} onChange={e => updateValue(e.target.value)} />
        </>
    )
}

export function CreateModal({ closeModal }: ModalProps) {
    const [title, setTitle] = useState("");
    const [imageUrl, setImageUrl] = useState("");
    const [price, setPrice] = useState(0);

    const { mutate, isSuccess, isPending } = useFoodDataMutate();

    const submit = () => {
        const foodData: FoodData = {
            title,
            url: imageUrl,
            price: Number(price)
        }

        mutate(foodData);
    }

    useEffect(() => {
        if (isSuccess) {
            closeModal();
            setTitle("");
            setImageUrl("");
            setPrice(0);
        }
    }, [isSuccess])
    
    return(
        <div className="modal-overlay">
            <div className="modal-body">
                <div className="modal-top">
                    <h2>Cadastre um novo item no cardápio</h2>
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
                    <button type="submit">Cadastrar</button>
                </form>
                <button onClick={submit} className="btn-secondary">
                    {isPending ? "Cadastrando..." : "Cadastrar"}
                </button>
            </div>
        </div>
    )
}