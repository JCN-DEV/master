'use strict';

describe('InstFinancialInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstFinancialInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstFinancialInfo = jasmine.createSpy('MockInstFinancialInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstFinancialInfo': MockInstFinancialInfo
        };
        createController = function() {
            $injector.get('$controller')("InstFinancialInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instFinancialInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
