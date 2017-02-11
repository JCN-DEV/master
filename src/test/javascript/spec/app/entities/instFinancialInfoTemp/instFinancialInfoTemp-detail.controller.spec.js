'use strict';

describe('InstFinancialInfoTemp Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstFinancialInfoTemp;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstFinancialInfoTemp = jasmine.createSpy('MockInstFinancialInfoTemp');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstFinancialInfoTemp': MockInstFinancialInfoTemp
        };
        createController = function() {
            $injector.get('$controller')("InstFinancialInfoTempDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instFinancialInfoTempUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
