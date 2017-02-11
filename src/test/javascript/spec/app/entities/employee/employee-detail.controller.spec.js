'use strict';

describe('Employee Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockEmployee, MockUser, MockInstitute, MockPayScale, MockApplicantEducation, MockTraining, MockSkill, MockReference, MockLang;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockEmployee = jasmine.createSpy('MockEmployee');
        MockUser = jasmine.createSpy('MockUser');
        MockInstitute = jasmine.createSpy('MockInstitute');
        MockPayScale = jasmine.createSpy('MockPayScale');
        MockApplicantEducation = jasmine.createSpy('MockApplicantEducation');
        MockTraining = jasmine.createSpy('MockTraining');
        MockSkill = jasmine.createSpy('MockSkill');
        MockReference = jasmine.createSpy('MockReference');
        MockLang = jasmine.createSpy('MockLang');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Employee': MockEmployee,
            'User': MockUser,
            'Institute': MockInstitute,
            'PayScale': MockPayScale,
            'ApplicantEducation': MockApplicantEducation,
            'Training': MockTraining,
            'Skill': MockSkill,
            'Reference': MockReference,
            'Lang': MockLang
        };
        createController = function() {
            $injector.get('$controller')("EmployeeDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:employeeUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
