'use strict';

describe('NameCnclApplicationEditLog Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockNameCnclApplicationEditLog, MockNameCnclApplication;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockNameCnclApplicationEditLog = jasmine.createSpy('MockNameCnclApplicationEditLog');
        MockNameCnclApplication = jasmine.createSpy('MockNameCnclApplication');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'NameCnclApplicationEditLog': MockNameCnclApplicationEditLog,
            'NameCnclApplication': MockNameCnclApplication
        };
        createController = function() {
            $injector.get('$controller')("NameCnclApplicationEditLogDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:nameCnclApplicationEditLogUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
