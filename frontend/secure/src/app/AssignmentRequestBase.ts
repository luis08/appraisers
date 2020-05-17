import {FormState} from './FormState';

export class AssignmentRequestBase {
  private formState = FormState.populate;

  setFailed(): void {
    this.formState = FormState.failed;
  }

  hasFailed(): boolean {
    return this.formState === FormState.failed;
  }

  setSuccess(): void {
    this.formState = FormState.succeeded;
  }

  hasSucceeded(): boolean {
    return this.formState === FormState.succeeded;
  }

  setWaiting(): void {
    this.formState = FormState.waiting;
  }

  isWaiting(): boolean {
    return this.formState === FormState.waiting;
  }

  showSubmitButton() {
    return !this.isWaiting();
  }
}
