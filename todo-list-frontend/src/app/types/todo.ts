export interface Todo {
  id: number;
  task: string;
  priority: Priority;
}

export enum Priority {
  LOW = 1,
  MEDIUM = 2,
  HIGH = 3
}
