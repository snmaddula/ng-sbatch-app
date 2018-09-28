import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SbatchLaunchComponent } from './sbatch-launch.component';

describe('SbatchLaunchComponent', () => {
  let component: SbatchLaunchComponent;
  let fixture: ComponentFixture<SbatchLaunchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SbatchLaunchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SbatchLaunchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
