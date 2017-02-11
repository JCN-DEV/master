'use strict';

describe('NameCnclApplication Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockNameCnclApplication, MockInstEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockNameCnclApplication = jasmine.createSpy('MockNameCnclApplication');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'NameCnclApplication': MockNameCnclApplication,
            'InstEmployee': MockInstEmployee
        };
        createController = function() {
            $injector.get('$controller')("NameCnclApplicationDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:nameCnclApplicationUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
