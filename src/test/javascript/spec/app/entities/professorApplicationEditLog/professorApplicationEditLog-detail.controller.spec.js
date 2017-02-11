'use strict';

describe('ProfessorApplicationEditLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProfessorApplicationEditLog, MockPrincipleApplication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProfessorApplicationEditLog = jasmine.createSpy('MockProfessorApplicationEditLog');
        MockPrincipleApplication = jasmine.createSpy('MockPrincipleApplication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ProfessorApplicationEditLog': MockProfessorApplicationEditLog,
            'PrincipleApplication': MockPrincipleApplication
        };
        createController = function() {
            $injector.get('$controller')("ProfessorApplicationEditLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:professorApplicationEditLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
