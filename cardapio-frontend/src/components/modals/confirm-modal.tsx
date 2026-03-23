import "./confirm-model.css";

interface ConfirmModalProps {
  onConfirm(): void
  onCancel(): void
}

export function ConfirmModal({ onConfirm, onCancel}: ConfirmModalProps){
  return (
    <div className="confirm-overlay">
      <div className="confirm-modal">
        <p className="confirm-message">
          Tem certeza que deseja excluir este item?
        </p>

        <div className="confirm-actions">
          <button className="confirm-btn confirm-yes" onClick={onConfirm}>
            Confirmar
          </button>
          <button className="confirm-btn confirm-no" onClick={onCancel}>
            Cancelar
          </button>
        </div>
      </div>
    </div>
  )
}