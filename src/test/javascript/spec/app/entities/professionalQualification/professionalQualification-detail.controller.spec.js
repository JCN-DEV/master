'use strict';

describe('ProfessionalQualification Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProfessionalQualification, MockEmployee, MockInstitute;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProfessionalQualification = jasmine.createSpy('MockProfessionalQualification');
        MockEmployee = jasmine.createSpy('MockEmployee');
        MockInstitute = jasmine.createSpy('MockInstitute');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ProfessionalQualification': MockProfessionalQualification,
            'Employee': MockEmployee,
            'Institute': MockInstitute
        };
        createController = function() {
            $injector.get('$controller')("ProfessionalQualificationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:professionalQualificationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
