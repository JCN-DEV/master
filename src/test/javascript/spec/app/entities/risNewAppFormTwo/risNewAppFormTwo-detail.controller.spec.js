'use strict';

describe('RisNewAppFormTwo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockRisNewAppFormTwo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockRisNewAppFormTwo = jasmine.createSpy('MockRisNewAppFormTwo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'RisNewAppFormTwo': MockRisNewAppFormTwo
        };
        createController = function() {
            $injector.get('$controller')("RisNewAppFormTwoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:risNewAppFormTwoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
