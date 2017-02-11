'use strict';

describe('ProfessorApplication Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProfessorApplication, MockInstEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProfessorApplication = jasmine.createSpy('MockProfessorApplication');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ProfessorApplication': MockProfessorApplication,
            'InstEmployee': MockInstEmployee
        };
        createController = function() {
            $injector.get('$controller')("ProfessorApplicationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:professorApplicationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
