import "./card.css";
import { useState } from "react";
import { useFoodDataMutate } from "../../hooks/useFoodDataMutate";
import { ConfirmModal } from "../modals/confirm-modal";

interface CardProps {
    code?: number;
    price: number;
    title: string;
    url: string;
    onEdit(): void;
}

export function Card({ code, price, title, url, onEdit }: CardProps) {

    const { deleteMutate } = useFoodDataMutate();

    const [confirmOpen, setConfirmOpen] = useState(false);

    function handleDeleteClick(){
        setConfirmOpen(true);
    }

    function confirmDelete(){
        if(code){
            deleteMutate.mutate(code);
        }
        setConfirmOpen(false);
    }

    return(
        <div className="card">
            <img src={url} alt="" />
            <h2>{title}</h2>
            <p><b>Valor:</b> {price}</p>
            <div className="card-btn">
                <button className="btn-edit" onClick={onEdit}>
                    Editar
                </button>
                <button className="btn-delete" onClick={handleDeleteClick}>
                    Excluir
                </button>
            </div>
            {confirmOpen && (
                <ConfirmModal
                    onConfirm={confirmDelete}
                    onCancel={() => setConfirmOpen(false)}
                />
            )}
        </div>
    )
}