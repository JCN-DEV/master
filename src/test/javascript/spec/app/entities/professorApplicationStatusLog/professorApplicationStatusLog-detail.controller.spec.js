'use strict';

describe('ProfessorApplicationStatusLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProfessorApplicationStatusLog, MockPrincipleApplication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProfessorApplicationStatusLog = jasmine.createSpy('MockProfessorApplicationStatusLog');
        MockPrincipleApplication = jasmine.createSpy('MockPrincipleApplication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'ProfessorApplicationStatusLog': MockProfessorApplicationStatusLog,
            'PrincipleApplication': MockPrincipleApplication
        };
        createController = function() {
            $injector.get('$controller')("ProfessorApplicationStatusLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:professorApplicationStatusLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
