'use strict';

describe('ApplicantEducation Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockApplicantEducation, MockEmployee, MockInstitute, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockApplicantEducation = jasmine.createSpy('MockApplicantEducation');
        MockEmployee = jasmine.createSpy('MockEmployee');
        MockInstitute = jasmine.createSpy('MockInstitute');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ApplicantEducation': MockApplicantEducation,
            'Employee': MockEmployee,
            'Institute': MockInstitute,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("ApplicantEducationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:applicantEducationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
