'use strict';

describe('NameCnclApplicationStatusLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockNameCnclApplicationStatusLog, MockNameCnclApplication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockNameCnclApplicationStatusLog = jasmine.createSpy('MockNameCnclApplicationStatusLog');
        MockNameCnclApplication = jasmine.createSpy('MockNameCnclApplication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'NameCnclApplicationStatusLog': MockNameCnclApplicationStatusLog,
            'NameCnclApplication': MockNameCnclApplication
        };
        createController = function() {
            $injector.get('$controller')("NameCnclApplicationStatusLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:nameCnclApplicationStatusLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
