'use strict';

describe('MpoTrade Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMpoTrade, MockCmsTrade, MockUser;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMpoTrade = jasmine.createSpy('MockMpoTrade');
        MockCmsTrade = jasmine.createSpy('MockCmsTrade');
        MockUser = jasmine.createSpy('MockUser');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'MpoTrade': MockMpoTrade,
            'CmsTrade': MockCmsTrade,
            'User': MockUser
        };
        createController = function() {
            $injector.get('$controller')("MpoTradeDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:mpoTradeUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
