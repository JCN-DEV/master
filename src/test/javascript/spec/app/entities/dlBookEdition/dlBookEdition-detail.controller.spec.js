'use strict';

describe('DlBookEdition Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDlBookEdition, MockDlBookInfo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDlBookEdition = jasmine.createSpy('MockDlBookEdition');
        MockDlBookInfo = jasmine.createSpy('MockDlBookInfo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DlBookEdition': MockDlBookEdition,
            'DlBookInfo': MockDlBookInfo
        };
        createController = function() {
            $injector.get('$controller')("DlBookEditionDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dlBookEditionUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
