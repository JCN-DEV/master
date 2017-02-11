'use strict';

describe('Experience Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockExperience, MockEmployee, MockInstitute, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockExperience = jasmine.createSpy('MockExperience');
        MockEmployee = jasmine.createSpy('MockEmployee');
        MockInstitute = jasmine.createSpy('MockInstitute');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Experience': MockExperience,
            'Employee': MockEmployee,
            'Institute': MockInstitute,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("ExperienceDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:experienceUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
