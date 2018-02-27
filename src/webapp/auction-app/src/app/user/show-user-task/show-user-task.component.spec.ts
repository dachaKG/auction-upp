import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowUserTaskComponent } from './show-user-task.component';

describe('ShowUserTaskComponent', () => {
  let component: ShowUserTaskComponent;
  let fixture: ComponentFixture<ShowUserTaskComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowUserTaskComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowUserTaskComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
