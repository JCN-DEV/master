'use strict';

describe('InstEmplBankInfo Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstEmplBankInfo, MockInstEmployee;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstEmplBankInfo = jasmine.createSpy('MockInstEmplBankInfo');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstEmplBankInfo': MockInstEmplBankInfo,
            'InstEmployee': MockInstEmployee
        };
        createController = function() {
            $injector.get('$controller')("InstEmplBankInfoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instEmplBankInfoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
